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
package com.sapi.apitest.datadriven.comparison;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapi.apitest.datadriven.CompareStrategyUtil;
import com.sapi.apitest.datadriven.comparison.impl.StrategyFactory;
import com.sapi.apitest.datadriven.entity.APIOutputPara;
import com.sapi.apitest.datadriven.utils.XmlAndJsonUtil;

/**
 * The comparison class that will execute the comparison according to the
 * various strategies.
 * 
 * @author wanhao01
 *
 */
public class ComparisonEngine {
    
    private static final Logger logger = LogManager.getLogger(Thread
            .currentThread().getStackTrace()[1].getClassName());
    
    /**
     * Compares online results with offline results by different kinds of result
     * verification rules 这里0
     * 
     * @param keysRulesMap
     * @param resultObject1
     * @param resultObject2
     * @param compResults
     * @return the comparison status
     * @throws Exception
     */
    public static String compareKeysInTwoObjects(
            Map<String, APIOutputPara> keysRulesMap, Object resultObject1,
            Object resultObject2, Map<String, String> compResultsMap)
            throws Exception {

        String compStatus = "PASS";
        Map<String, String> result1 = parseParaWithDetailFromJosn(
                resultObject1, "");
        Map<String, String> result2 = parseParaWithDetailFromJosn(
                resultObject2, "");

        Set<String> keySet1 = result1.keySet();
        for (String paraName : keySet1) {
            String value1 = result1.get(paraName);
            String value2 = result2.get(paraName);
            result2.remove(paraName);
            compStatus = compareWithRule(keysRulesMap, compResultsMap,
                    compStatus, paraName, value1, value2);
        }
        Set<String> keySet2 = result2.keySet();
        for (String paraName : keySet2) {
            String value1 = null;
            String value2 = result2.get(paraName);
            compStatus = compareWithRule(keysRulesMap, compResultsMap,
                    compStatus, paraName, value1, value2);
        }

        for (String paraName : keysRulesMap.keySet()) {
            APIOutputPara apiOutputPara = keysRulesMap.get(paraName);
            if (apiOutputPara == null) {
                continue;
            }
            if (apiOutputPara.getCheckRule().equals(
                    CompareStrategyUtil.RULE_EXIST)) {
                StringBuffer buffer = new StringBuffer();
                String value1 = result1.get(paraName);
                String value2 = result2.get(paraName);
                compStatus = executeComparison(compResultsMap, compStatus,
                        paraName, apiOutputPara, buffer, value1, value2);
            }
        }

        return compStatus;
    }

    /**
     * Get the comparison rule, and execute the comparison with the rules
     * specified.
     * 
     * @param keysRulesMap
     * @param compResultsMap
     * @param compStatus
     * @param paraName
     * @param value1
     * @param value2
     * @return
     */
    private static String compareWithRule(
            Map<String, APIOutputPara> keysRulesMap,
            Map<String, String> compResultsMap, String compStatus,
            String paraName, String value1, String value2) {
        String paraRealName = parseParamName(paraName);
        APIOutputPara apiOutputPara = keysRulesMap.get(paraRealName);
        if (apiOutputPara == null) {
            apiOutputPara = new APIOutputPara();
            apiOutputPara.setParaName(paraRealName);
            apiOutputPara.setCheckRule(CompareStrategyUtil.RULE_EQUAL);
            keysRulesMap.put(paraRealName, apiOutputPara);
        }
        StringBuffer buffer = new StringBuffer();
        if (apiOutputPara != null
                && !apiOutputPara.getCheckRule().equals(
                        CompareStrategyUtil.RULE_NOCHECK)) {
            compStatus = executeComparison(compResultsMap, compStatus,
                    paraName, apiOutputPara, buffer, value1, value2);
        }
        return compStatus;
    }

    /**
     * Execute the comparison with the specific compare rules.
     * @param compResultsMap
     * @param compStatus
     * @param paraName
     * @param apiOutputPara
     * @param buffer
     * @param value1
     * @param value2
     * @return
     */
    private static String executeComparison(Map<String, String> compResultsMap,
            String compStatus, String paraName, APIOutputPara apiOutputPara,
            StringBuffer buffer, String value1, String value2) {
        String statusTemp = StrategyFactory.getStrategy(
                apiOutputPara.getCheckRule()).compare(value1, value2,
                    apiOutputPara, buffer);
        if (statusTemp.equals("FAIL")) {
            compStatus = "FAIL";
        }
        String tmpCompResults = "("
                + paraName
                + "-"
                + apiOutputPara.getCheckRule()
                + "-"
                + (buffer.toString().equals("") ? "PASS" : "FAIL-"
                        + buffer.toString()) + ");<br />";
        compResultsMap.put(paraName, tmpCompResults);
        return compStatus;
    }

    /**
     * compare key in an array and ignore sequence.
     * 
     * @param keysRulesMap
     * @param resultObject1
     * @param resultObject2
     * @param compResultsMap
     * @return
     * @throws Exception
     */
    public static void compareKeysInTwoObjectsWithoutOrder(
            Map<String, APIOutputPara> keysRulesMap, Object resultObject1,
            Object resultObject2, Map<String, String> compResultsMap)
            throws Exception {
        parseParaWithDetailFromJosn(resultObject1, "");
        parseParaWithDetailFromJosn(resultObject2, "");
    }

    /**
     * parsing the comparison details that define the compare rule for each
     * key-value set from a json entity.
     * 
     * @param jsonObject
     * @param pre
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseParaWithDetailFromJosn(
            Object jsonObject, String pre) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        new ObjectMapper();
        Set<String> keySet = null;
        Map<String, ?> jsonMap = null;
        if (jsonObject instanceof LinkedHashMap) {
            jsonMap = (LinkedHashMap<String, ?>) jsonObject;
            keySet = jsonMap.keySet();
            for (String string : keySet) {
                Object value = jsonMap.get(string);
                if (value instanceof LinkedHashMap) {
                    Map<String, String> temp = parseParaWithDetailFromJosn(
                            (LinkedHashMap<?, ?>) value, pre + string + ".");
                    if (temp != null) {
                        result.putAll(temp);
                    }

                } else if (value instanceof List) {
                    int i = 0;
                    result.put(pre + string + "#", ((List<?>) value).size()
                            + "");
                    for (Object obj : (List<?>) value) {
                        Map<String, String> temp = parseParaWithDetailFromJosn(
                                obj, pre + string + "#" + i + ".");
                        if (temp != null) {
                            result.putAll(temp);
                        } else {
                            if (obj != null) {
                                result.put(pre + string + "#." + i,
                                        obj.toString());
                            }
                        }
                        i++;
                    }
                } else {
                    result.put(pre.equals("") ? string : pre + string,
                            value == null ? null : value.toString());
                }
            }
            return result;

        } else if (jsonObject instanceof List) {
            int i = 0;
            result.put(pre + "#", ((List<?>) jsonObject).size() + "");
            for (Object obj : (List<?>) jsonObject) {
                Map<String, String> temp = parseParaWithDetailFromJosn(obj, pre
                        + "#" + i + ".");
                if (temp != null) {
                    result.putAll(temp);
                } else {
                    if (obj != null) {
                        result.put(pre + "#." + i, obj.toString());
                    }
                }
                i++;
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * Parsing parameter name from a string.
     * 
     * @param name
     * @return
     */
    private static String parseParamName(String name) {
        if (name == null) {
            return null;
        }
        char[] chars = name.toCharArray();
        String result = "";
        boolean begin = false;
        for (char c : chars) {
            if (begin && !Character.isDigit(c)) {
                result += c;
                begin = false;
                continue;
            } else if (begin && Character.isDigit(c)) {
                continue;
            }
            if (c == '#') {
                begin = true;
            }
            result += c;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(parseParamName("#12.#21.bdd#"));
        String s = "{\"location\":[1233,23344]}";
        String s1 = "{\"location\":[1243,23341]}";
        ObjectMapper mapper = new ObjectMapper();
        Object o0 = mapper.readValue(s, Object.class);
        Object o1 = mapper.readValue(s1, Object.class);
        Set<String> rulesSet = XmlAndJsonUtil.parseParaFromJosn(s1, "json");
        Map<String, APIOutputPara> rules = new HashMap<String, APIOutputPara>();
        for (String string : rulesSet) {
            APIOutputPara para = new APIOutputPara();
            para.setParaName(string);
            para.setCheckRule(CompareStrategyUtil.RULE_PRECISION);
            para.setParaValue("12");
            para.setType("json");
            rules.put(string, para);
        }
        Map<String, String> compResultsMap = new LinkedHashMap<String, String>();
        ComparisonEngine.compareKeysInTwoObjects(rules, o0, o1, compResultsMap);

        logger.info(parseParaWithDetailFromJosn(o0, ""));
    }

}
