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
package com.sapi.apitest.datadriven.comparison.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.comparison.ComparisonStrategy;
import com.sapi.apitest.datadriven.entity.APIOutputPara;

/***
 * The strategy used to execute the regex match.
 * 
 * @author wanhao01
 *
 */
public class RegularMatchStrategy implements ComparisonStrategy {

    private final String errorCompareFailedTemplate = "reason : key %s VS %s doesn't match the compare rule.";
    private final String errorCompareException = "reason : key %s VS %s error when doing the regex match.";
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    /**
     * The strategy used to execute the regex match.
     */
    @SuppressWarnings("unused")
    @Override
    public String compare(Object finalOnlineParamValue,
            Object finalRelativeParamValue, APIOutputPara apiOutputPara,
            StringBuffer compResults) {
        String paramName = apiOutputPara.getParaName();
        String compStatus = ComparisonStrategy.PASS;
        if (finalOnlineParamValue == finalRelativeParamValue) {
            // offlineCompResults.append(paramName + ":Pass" + ";");
            return compStatus;
        }
        try {
            if (finalOnlineParamValue != null
                    && finalRelativeParamValue != null) {
                String regularValue = apiOutputPara.getParaValue();
                Pattern pattern = Pattern.compile(regularValue);
                Matcher matcherOnlineParamValue = pattern
                        .matcher(finalOnlineParamValue.toString());
                Matcher matcherRelativeParamValue = pattern
                        .matcher(finalRelativeParamValue.toString());
                if (matcherOnlineParamValue.find()
                        && matcherRelativeParamValue.find()) {
                    // offlineCompResults.append(paramName +
                    // ":Pass" + ";");
                    logger.info("not found");
                } else {
                    compStatus = ComparisonStrategy.FAIL;
                    // compResults.append(paramName +
                    // ":FAIL" + ",精度匹配不通过;");
                    compResults.append(String.format(
                            errorCompareFailedTemplate, finalOnlineParamValue,
                            finalRelativeParamValue));
                }
            } else {
                // compResults.append(paramName + ":FAIL" +
                // ",精度匹配不通过,其中一个参数为空;");
                compResults.append(String.format(errorCompareFailedTemplate,
                        finalOnlineParamValue, finalRelativeParamValue));
            }

        } catch (Exception e) {
            compStatus = ComparisonStrategy.FAIL;

            // compResults.append(paramName + ":FAIL" +
            // ",类型转换错误，精度匹配不通过;");
            compResults.append(String.format(errorCompareException,
                    finalOnlineParamValue, finalRelativeParamValue));
        }
        return compStatus;
    }
}