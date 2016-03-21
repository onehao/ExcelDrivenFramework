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
package com.sapi.apitest.datadriven.exceptions;

/***
 * The exception that define to reflect the the condition that the input test
 * case is not valid for execution.
 * 
 * @author wanhao01
 *
 */
public class URLInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    public URLInvalidException(String message) {
        super(message);
    }
}