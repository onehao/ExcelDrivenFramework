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

import com.sapi.apitest.datadriven.comparison.ComparisonStrategy;
import com.sapi.apitest.datadriven.entity.APIOutputPara;

/***
 * The strategy used to execute the equals functionality for the specific
 * key-value set.
 * 
 * @author wanhao01
 *
 */
public class ExactMatchStrategy implements ComparisonStrategy {
    private final String errorNotMatch = "<font color=\"red\">reason:key %s VS %s - "
            + "doesn't match the compare rule.</font>";

    /**
     * Execute equals compare for the specific key-value set.
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

        if (finalRelativeParamValue != null && finalOnlineParamValue != null
                && finalRelativeParamValue.equals(finalOnlineParamValue)) {
            // offlineCompResults.append(paramName + ":Pass" + ";");
            return compStatus;
        }

        // 无法精确匹配
        compStatus = ComparisonStrategy.FAIL;
        // compResults.append(paramName + ":FAIL" + ",key equals failed;");

        compResults.append(String.format(errorNotMatch, finalOnlineParamValue,
                finalRelativeParamValue));

        return compStatus;
    }
}