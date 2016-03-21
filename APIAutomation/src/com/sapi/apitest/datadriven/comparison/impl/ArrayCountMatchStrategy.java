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

import java.util.List;

import com.sapi.apitest.datadriven.comparison.ComparisonStrategy;
import com.sapi.apitest.datadriven.entity.APIOutputPara;

/***
 * The strategy used to compare the key-value especially an array according to
 * the size of the array.
 * 
 * @author wanhao01
 *
 */
public class ArrayCountMatchStrategy implements ComparisonStrategy {
    private final String errorCountNotMatch = "reason : the count doesn't match.";
    private final String errorResultNotArray = "reason : the result doesn't an array.";

    /**
     * Compare the size of the array for the online and offline env. Record the
     * result after execution.
     * @param finalOnlineParamValue: the final online parameter value.
     * @param finalRelativeParamValue: the final relative parameter value.
     * @param apiOutputPara: api out para.
     * @param compResults: compare results.
     * @return compare status.
     */
    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    public String compare(Object finalOnlineParamValue,
            Object finalRelativeParamValue, APIOutputPara apiOutputPara,
            StringBuffer compResults) {
        String paramName = apiOutputPara.getParaName();
        String compStatus = ComparisonStrategy.PASS;
        if (finalRelativeParamValue == finalOnlineParamValue) {
            // offlineCompResults.append(paramName + ":PASS" + ";");
            return compStatus;
        }

        if ((finalRelativeParamValue instanceof List)
                && (finalOnlineParamValue instanceof List)) {
            List<Object> tmpofflineMap = (List<Object>) finalRelativeParamValue;
            List<Object> tmponlineMap = (List<Object>) finalOnlineParamValue;
            if (tmpofflineMap.size() != tmponlineMap.size()) {
                compStatus = ComparisonStrategy.FAIL;
                // compResults.append(paramName + ":FAIL" +
                // ",数目不匹配;");
                compResults.append(errorCountNotMatch);
            }
        } else {
            compStatus = ComparisonStrategy.FAIL;
            // compResults.append(paramName + ":FAIL,原因结果 不是一个数组,");
            compResults.append(errorResultNotArray);
        }
        return compStatus;
    }

}
