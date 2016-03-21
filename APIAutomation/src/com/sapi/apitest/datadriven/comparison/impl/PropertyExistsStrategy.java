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
 * The strategy used to check whether the specific key exist in the json string
 * for the actual result.
 * 
 * @author wanhao01
 *
 */
public class PropertyExistsStrategy implements ComparisonStrategy {

    private final String errorAttributeNotExist = "reason : the attribute doesn't exist.";
    /**
     * The strategy used to check whether the specific key exist in the json
     * string for the actual result.
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
            // compResults.append(paramName + ":FAIL" + ",属性不存在;");
            compResults.append(errorAttributeNotExist);

        }
        return compStatus;
    }
}