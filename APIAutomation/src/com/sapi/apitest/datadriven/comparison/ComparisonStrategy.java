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

import com.sapi.apitest.datadriven.entity.APIOutputPara;

/**
 * The strategy based interface which defined the basic structure for the
 * inheritance.
 * 
 * @author wanhao01
 *
 */
public interface ComparisonStrategy {
    static final String PASS = "PASS";
    static final String FAIL = "FAIL";
    
    // The Comparison implementation should implement the compare strategy.
    public String compare(Object finalOnlineParamValue,
            Object finalRelativeParamValue, APIOutputPara apiOutputPara,
            StringBuffer compResults);
}