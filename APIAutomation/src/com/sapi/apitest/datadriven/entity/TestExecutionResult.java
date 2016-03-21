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
package com.sapi.apitest.datadriven.entity;

/***
 * The data structure that used to store the test case execution result.
 * 
 * @author wanhao01
 *
 */
public class TestExecutionResult {

    private String url0;
    private String url1;
    private String compState;
    private String compDetail;
    private String testDescription;

    /**
     * @return the testDescription
     */
    public String getTestDescription() {
        return testDescription;
    }

    /**
     * @param testDescription
     *            the testDescription to set
     */
    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getUrl0() {
        return url0;
    }

    public void setUrl0(String url0) {
        this.url0 = url0;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getCompState() {
        return compState;
    }

    public void setCompState(String compState) {
        this.compState = compState;
    }

    public String getCompDetail() {
        return compDetail;
    }

    public void setCompDetail(String compDetail) {
        this.compDetail = compDetail;
    }
}