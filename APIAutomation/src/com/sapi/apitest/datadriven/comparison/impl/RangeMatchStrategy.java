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

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.comparison.ComparisonStrategy;
import com.sapi.apitest.datadriven.entity.APIOutputPara;

/***
 * The strategy used to check whether the distinct value from actual value and
 * expected values is in an acceptable range.
 * 
 * @author wanhao01
 *
 */
public class RangeMatchStrategy implements ComparisonStrategy {

    private final String errorCompareFailedTemplate = "reason : key %s VS %s doesn't match the compare rule.";
    private final String errorCompareException = "reason : key %s VS %s type conversion failed, "
            + "and range comparison failed.";
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    /***
     * The strategy used to check whether the distinct value from actual value
     * and expected values is in an acceptable range.
     *
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
                String rangeValue = apiOutputPara.getParaValue();
                double rangeValueNum = Double.parseDouble(rangeValue);
                double finalOnlineParamValueNum = Double
                        .parseDouble(finalOnlineParamValue.toString());
                double finalRelativeParamValueNum = Double
                        .parseDouble(finalRelativeParamValue.toString());
                DecimalFormat df = new DecimalFormat("0.0000");
                String rangeStr = df.format(Math.abs(finalOnlineParamValueNum
                        - finalRelativeParamValueNum));
                Double range = Double.parseDouble(rangeStr);
                if (range <= rangeValueNum) {
                    // offlineCompResults.append(paramName +
                    // ":Pass" + ";");
                    logger.info("range <= rangeValueNum");
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

            // e.printStackTrace();
        }
        return compStatus;

    }
}