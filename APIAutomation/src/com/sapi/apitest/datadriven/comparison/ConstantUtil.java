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

import com.sapi.apitest.datadriven.CompareStrategyUtil;

/**
 * 
 * This ConstantUtil class is used to define constant variables.
 * 
 * @author liangmanman@baidu.com
 *
 */
public class ConstantUtil {

    // Define the return codes
    public static final int RETCODE_ERROR = 1;
    public static final int RETCODE_CORRECT = 0;

    // Define the json string comparison status
    public static final String COMP_STATUS_PASS = "PASS";
    public static final String COMP_STATUS_FAIL = "FAIL";

    // Define all of execution engine
    public static final String FUNC_JAVA_HTTP_CLIENT = "java_httpclient";
    public static final String FUNC_JAVA_CURL_CLIENT = "java_curl";
    public static final String FUNC_JAVA_SOAP_CLIENT = "java_soap";
    public static final String FUNC_PHP_CURL_CLIENT = "php_curl";
    public static final String FUNC_PHP_SOAP_CLIENT = "php_soap";

    // Define all of supported execution engine array
    public static final String[] REQUEST_FUNCTION_SUPPORTED = {
            FUNC_JAVA_HTTP_CLIENT, FUNC_JAVA_CURL_CLIENT, FUNC_JAVA_SOAP_CLIENT };

    // Define the http request type
    public static final String REQUEST_TYPE_GET = "get";
    public static final String REQUEST_TYPE_POST = "post";

    // Define all of supported request type
    public static final String[] REQUEST_TYPE_SUPPORTED = { REQUEST_TYPE_GET,
            REQUEST_TYPE_POST };

    // Define check rules
    public static final String CHECK_RULE_EXACT_MATCH = CompareStrategyUtil.RULE_EQUAL;
    public static final String CHECK_RULE_NOT_NULL = CompareStrategyUtil.RULE_NOTNULL;
    public static final String CHECK_RULE_PROPERTY_EXSIT = CompareStrategyUtil.RULE_EXIST;
    public static final String CHECK_RULE_COUNT_MATCH = CompareStrategyUtil.RULE_COUNT;
    public static final String CHECK_RULE_REGULAR_MATCH = CompareStrategyUtil.RULE_REGEX;
    public static final String CHECK_RULE_RANGE_MATCH = CompareStrategyUtil.RULE_PRECISION;
    public static final String CHECK_RULE_NOT_CHECK = CompareStrategyUtil.RULE_NOCHECK;

    // Define comparison type
    public static final String COMPARE_TYPE_ONLINE_OFFLINE = "线上线下";
    public static final String COMPARE_TYPE_ONLINE_EXPECT = "线上与期望值";
    public static final String COMPARE_TYPE_OFFLINE_EXPECT = "线下与期望值";

    // Define string type
    public static final String STRING_TYPE_JSON = "json";
    public static final String STRING_TYPE_XML = "xml";
    public static final String STRING_TYPE_HTML_TEXT = "text";
}
