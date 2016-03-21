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
package com.sapi.apitest.datadriven.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Annotation that used to tag the test suite which will group the test cases.
 * 
 * @author wanhao01
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestSuite {
}