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
import com.sapi.apitest.datadriven.comparison.ConstantUtil;

/***
 * Factory used the construct the strategies.
 * 
 * @author wanhao01
 *
 */
public class StrategyFactory {
    
    /**
     * Get the comparison strategy according to the type.
     * @param strategyType
     * @return
     */
    public static ComparisonStrategy getStrategy(String strategyType) {
        ComparisonStrategy compStrategy = null;
        switch (strategyType) {
            case ConstantUtil.CHECK_RULE_EXACT_MATCH:
                compStrategy = new ExactMatchStrategy();
                break;
            case ConstantUtil.CHECK_RULE_RANGE_MATCH:
                compStrategy = new RangeMatchStrategy();
                break;
            case ConstantUtil.CHECK_RULE_NOT_NULL:
                compStrategy = new NonNullStrategy();
                break;
            case ConstantUtil.CHECK_RULE_PROPERTY_EXSIT:
                compStrategy = new PropertyExistsStrategy();
                break;
            case ConstantUtil.CHECK_RULE_COUNT_MATCH:
                compStrategy = new ArrayCountMatchStrategy();
                break;
            case ConstantUtil.CHECK_RULE_REGULAR_MATCH:
                compStrategy = new RegularMatchStrategy();
                break;
        }
        return compStrategy;
    }
}