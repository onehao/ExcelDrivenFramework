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
 * Annotations that used to tag and reflect the test case attributes, as long as
 * the attribute is tagged with this annotation the the attribute will be
 * constructed automatically.
 * 
 * @author wanhao01
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCaseAttribute {
}