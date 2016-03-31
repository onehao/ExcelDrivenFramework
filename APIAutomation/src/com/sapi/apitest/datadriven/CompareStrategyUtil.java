/*
 * ################################################################################
 * 
 *    Copyright (c) 2015 Baidu.com, Inc. All Rights Reserved
 *
 *  version: v1
 *  
 *  
 * ################################################################################
 */
package com.sapi.apitest.datadriven;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.concurrency.KeyAnalyzer;
import com.sapi.apitest.datadriven.concurrency.ThreadManager;
import com.sapi.apitest.datadriven.entity.APIOutputPara;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;

/***
 * The class provided the functionality used to handle comparison rules.
 * 
 * @author wanhao01
 * 
 *
 */
public class CompareStrategyUtil {

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    // The info structure.
    private static final String COMPAREINFO = "#the compare rules are: %s, %s, %s, %s, %s, %s, %s.\r\n\r\n";

    // The following are the rules format used in this framework.
    public static final String ENTIRE_STRING_EQUALS = "entireStringEquals";
    public static final String STRING_REGEX = "stringRegex";
    public static final String RULE_EQUAL = "equal";
    public static final String RULE_NOTNULL = "notnull";
    public static final String RULE_EXIST = "exist";
    public static final String RULE_COUNT = "count";
    public static final String RULE_REGEX = "regex";
    public static final String RULE_PRECISION = "precision";
    public static final String RULE_NOCHECK = "nocheck";
    public static final String ENCODING = "utf8";
    
    /**
     * Generate the comparison rule for the specific testcases.
     * @param testcases: the test case list.
     * @param rulePath: the path of the rule file.
     */
    public static void generatCompareRule(List<ExcelDrivenTestCase> testcases,
            String rulePath, int threadCount) {
        Set<String> keys = getOutputKey(testcases, threadCount);
        saveRules(rulePath, keys);
    }

    /**
     * Save the rule of the key-rule to the local file.
     * Name specified as excelName_worksheetName.testrules
     * @param savePath: the save path.
     * @param keys: the json key set.
     */
    public static void saveRules(String savePath, Set<String> keys) {
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(new File(
                    savePath)), ENCODING);
            BufferedWriter bwriter = new BufferedWriter(writer);
            bwriter.write(String.format(COMPAREINFO, RULE_EQUAL,
                    RULE_NOTNULL, RULE_EXIST, RULE_COUNT, RULE_REGEX,
                    RULE_PRECISION, RULE_NOCHECK));
            bwriter.write(String.format("%s=false\r\n", ENTIRE_STRING_EQUALS));
            bwriter.write(String.format("%s=\r\n", STRING_REGEX));
            for (String s : keys) {
                bwriter.write(s + "=equal\r\n");
            }

            bwriter.close();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getStackTrace());
        } catch (FileNotFoundException e) {
            logger.error(e.getStackTrace());
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    /**
     * Get filename from \\context1\\context2\\filename.extension.
     * 
     * @param location: the path of the file. 
     * @return the file name.
     */
    public static String getNameFromLocation(String location) {
        int index = 0;
        if(location == null){
        	return "";
        }
        // On UNIX systems the value of this field is '/'; on Microsoft Windows
        // systems it is '\\'.
        // but in the config file we may using the '\\' for both windows and
        // linux.
        if (location.contains(File.separator)) {
            index = location.lastIndexOf(File.separator);
        } else if (location.contains("\\")) {
            index = location.lastIndexOf("\\");
        } else {
            index = location.lastIndexOf("/");
        }

        String fileName = location.substring(index + 1);

        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }

        return fileName;
    }

    /**
     * Get the result key structure of the specific API.
     * @param testcases
     * @param threadCount
     * @return
     */
    private static Set<String> getOutputKey(
            List<ExcelDrivenTestCase> testcases, int threadCount) {
        try {
            int block = testcases.size() / threadCount;
            List<Runnable> keyAnalyzers = new ArrayList<Runnable>();
            for (int i = 0; i < threadCount; i++) {
                KeyAnalyzer keyAnalyzer = new KeyAnalyzer();
                // keyAnalyzer.setDomain(domain);
                if (i == threadCount - 1) {
                    keyAnalyzer.setTestcases(testcases.subList(i * block,
                            testcases.size()));
                } else {
                    keyAnalyzer.setTestcases(testcases.subList(i * block, i
                            * block + block));
                }
                keyAnalyzers.add(keyAnalyzer);
            }
            List<Thread> threadPool = ThreadManager.getThreadPool(keyAnalyzers);

            ThreadManager.startThreadPool(threadPool);

            ThreadManager.joinThreadPool(threadPool);

            Set<String> result = new LinkedHashSet<String>();
            for (Runnable thread : keyAnalyzers) {
                KeyAnalyzer keyAnalyzer = (KeyAnalyzer) thread;
                result.addAll(keyAnalyzer.getKeys());
            }
            return result;
        } catch (InterruptedException e) {
            logger.error(e.getStackTrace());
        }
        return null;
    }

    /**
     * Read the comparison key-rule pair and store in a map.
     * @param rulePath
     * @return the key-rule map.
     */
    public static Map<String, APIOutputPara> readRules(String rulePath) {
        Map<String, APIOutputPara> rules = new HashMap<String, APIOutputPara>();
        InputStreamReader freader;
        Throwable throwable = null;
        try {
            freader = new InputStreamReader(new FileInputStream(rulePath),
                    "utf8");
            BufferedReader reader = new BufferedReader(freader);
            String str = reader.readLine();
            while (str != null) {
                if (str.startsWith("#") || str.trim().length() <= 0) {
                    str = reader.readLine();
                    continue;
                }
                try {
                    if (str.trim().contains(ENTIRE_STRING_EQUALS)) {
                        if (str.trim().equals(
                                String.format("%s=true", ENTIRE_STRING_EQUALS))) {
                            rules.put(String.format("%s=true",
                                    ENTIRE_STRING_EQUALS), null);
                        }

                    } else if (str.trim().startsWith(
                            String.format("%s=", STRING_REGEX))) {
                        if (!str.trim().equals(
                                String.format("%s=", STRING_REGEX))) {
                            String rexString = str.trim().substring(5,
                                    str.trim().length());
                            try {
                                Pattern.compile(rexString);
                                rules.put(str, null);
                            } catch (Exception e) {
                                logger.error("error when parsing the regex string");
                            }
                        }
                    } else {
                        String key = str.substring(0, str.indexOf(":"));
                        if (key != null && !key.trim().equals("")) {
                            str = str.substring(str.indexOf(":") + 1,
                                    str.length());
                            if (str != null && str.length() > 0) {
                                String type = str
                                        .substring(0, str.indexOf("="));
                                if (type != null
                                        && (type.equals("json") || type
                                                .equals("xml"))) {
                                    str = str.substring(str.indexOf("=") + 1,
                                            str.length());
                                    if (str.equals(RULE_EQUAL)
                                            || str.equals(RULE_NOTNULL)
                                            || str.equals(RULE_EXIST)
                                            || str.equals(RULE_COUNT)
                                            || str.equals(RULE_NOCHECK)) {
                                        APIOutputPara para = new APIOutputPara();
                                        para.setParaName(key);
                                        para.setCheckRule(str);
                                        para.setType(type);
                                        rules.put(key, para);
                                    } else if (str.startsWith(RULE_REGEX)) {
                                        APIOutputPara para = new APIOutputPara();
                                        para.setParaName(key);
                                        para.setCheckRule(RULE_REGEX);
                                        para.setType(type);
                                        para.setParaValue(str.substring(
                                                str.indexOf(":") + 1,
                                                str.length()));
                                        rules.put(key, para);

                                    } else if (str.startsWith(RULE_PRECISION)) {
                                        APIOutputPara para = new APIOutputPara();
                                        para.setParaName(key);
                                        para.setCheckRule(RULE_PRECISION);
                                        para.setType(type);
                                        para.setParaValue(str.substring(
                                                str.indexOf(":") + 1,
                                                str.length()));
                                        rules.put(key, para);
                                    }
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.error("there's error when parsing key: {}", str);
                    logger.error(e.getStackTrace());
                }
                str = reader.readLine();
            }
            freader.close();
            reader.close();
            return rules;
        } catch (UnsupportedEncodingException e) {
            throwable = e;
        } catch (FileNotFoundException e) {
            throwable = e;
        } catch (IOException e) {
            throwable = e;
        } finally {
            if (throwable != null) {
                logger.error(throwable);
            }
        }
        return null;
    }
}