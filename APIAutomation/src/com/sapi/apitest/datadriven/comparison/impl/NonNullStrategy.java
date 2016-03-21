/*
 * ################################################################################
 * 
 *    Copyright (c) 2015 Baidu.com, Inc. All Rights Reserved
 *
 *    version: v1
 *  
 *  
 * ################################################################################
 */
package com.sapi.apitest.datadriven.comparison.impl;

import com.sapi.apitest.datadriven.comparison.ComparisonStrategy;
import com.sapi.apitest.datadriven.entity.APIOutputPara;

/***
 * The strategy used to check whether the value of a specific key is null.
 * 
 * @author wanhao01
 *
 */
public class NonNullStrategy implements ComparisonStrategy {
    private final String errorReasonTemplate = "reason:key%s VS %sdoesn't match the rule. ";

    /***
     * The strategy used to check whether the value of a specific key is null.
     * 
     */
    @SuppressWarnings("unused")
    @Override
    public String compare(Object finalOnlineParamValue,
            Object finalRelativeParamValue, APIOutputPara apiOutputPara,
            StringBuffer compResults) {
        String paramName = apiOutputPara.getParaName();
        String compStatus = ComparisonStrategy.PASS;

        if (finalOnlineParamValue == null || finalRelativeParamValue == null) {
            compStatus = ComparisonStrategy.FAIL;
            // compResults.append(paramName + ":FAIL" + ",不满足非空条件;");
            compResults.append(String.format(errorReasonTemplate,
                    finalOnlineParamValue, finalRelativeParamValue));

        }

        return compStatus;
    }
}