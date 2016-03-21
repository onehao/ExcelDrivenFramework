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
package com.sapi.apitest.datadriven.concurrency;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;
import com.sapi.apitest.datadriven.utils.HttpRequest;
import com.sapi.apitest.datadriven.utils.XmlAndJsonUtil;

/**
 * The analyzer used the analyze the possible output key-value sets for a API,
 * in general the output is all the possible key-values pairs for all the test
 * cases.
 * 
 * @author wanhao01
 * 
 * 
 */
public class KeyAnalyzer implements Runnable {

    private String domain;
    private List<ExcelDrivenTestCase> testcases = new ArrayList<ExcelDrivenTestCase>();
    private Set<String> keys = new LinkedHashSet<String>();
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<ExcelDrivenTestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<ExcelDrivenTestCase> testcases) {
        this.testcases = testcases;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    /**
     * Get all the possible key-value pairs for the given test cases.
     * 
     * @param testcases
     * @return
     */
    private Set<String> getOutputKey(List<ExcelDrivenTestCase> testcases) {
        Set<String> results = new LinkedHashSet<>();
        for (ExcelDrivenTestCase testcase : testcases) {
            String resultString = HttpRequest.sendGet("http://" + testcase.getTestURL());
            if (XmlAndJsonUtil.isJsonString(resultString)) {
                results.addAll(XmlAndJsonUtil.parseParaFromJosn(resultString,
                        "json"));
            } else if (XmlAndJsonUtil.isXML(resultString)) {
                results.addAll(XmlAndJsonUtil.parseParaFromXML(resultString));
            } else {
                logger.warn(resultString);
            }
        }
        return results;
    }

    @Override
    public void run() {
        logger.info("enter run function. ");
        if (testcases.size() == 0) {
            return;
        }
        keys = getOutputKey(testcases);
    }
}