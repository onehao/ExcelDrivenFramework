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
 * Annotation that used to mark the test case, and will later be extracted and
 * executed by the excel driven framework.
 * 
 * @author wanhao01
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    // public int caseid(); // used to map to the data pool.

    // The description of the test.
    public String description() default "fuzz test";

    // whenever there's bug failed testcases, before fixing the bug the case may
    // always fail, add this for annotate these testcases.
    public boolean isFastFail() default false;

    // The priority of the test case.
    public int priority() default 3;

    // The property used when isFastFail is true.
    // The url of the bug.
    public String bugUrl() default "";
}
