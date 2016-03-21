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

import com.sapi.apitest.datadriven.annotations.TestCaseAttribute;

/***
 * The test case data structure.
 * 
 * @author wanhao01
 *
 */
public class ExcelDrivenTestCase {

    public enum RequestType {
        GET, POST, PUT, DELETE
    }

    public enum Priority {
        ONE, TWO, THREE, FOUR
    }

    public enum Result {
        PASS, FAIL, FASTFAIL, INCONCLUSIVE
    }

    @TestCaseAttribute
    private String requestType;

    /**
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the testURL
     */
    public String getTestURL() {
        return testURL;
    }

    /**
     * @param testURL
     *            the testURL to set
     */
    public void setTestURL(String testURL) {
        this.testURL = testURL;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the expectedResult
     */
    public String getExpectedResult() {
        return expectedResult;
    }

    /**
     * @param expectedResult
     *            the expectedResult to set
     */
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    /**
     * @return the isIgnore
     */
    public String getIsIgnore() {
        return isIgnore;
    }

    /**
     * @param isIgnore
     *            the isIgnore to set
     */
    public void setIsIgnore(String isIgnore) {
        this.isIgnore = isIgnore;
    }

    /**
     * @return the isFastFail
     */
    public String getIsFastFail() {
        return isFastFail;
    }

    /**
     * @param isFastFail
     *            the isFastFail to set
     */
    public void setIsFastFail(String isFastFail) {
        this.isFastFail = isFastFail;
    }

    /**
     * @return the bugURL
     */
    public String getBugURL() {
        return bugURL;
    }

    /**
     * @param bugURL
     *            the bugURL to set
     */
    public void setBugURL(String bugURL) {
        this.bugURL = bugURL;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * @return the apiName
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * @param apiName
     *            the apiName to set
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * @return the apiVersion
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * @param apiVersion
     *            the apiVersion to set
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * @return the releaseVersion
     */
    public String getReleaseVersion() {
        return releaseVersion;
    }

    /**
     * @param releaseVersion
     *            the releaseVersion to set
     */
    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @TestCaseAttribute
    private String testURL;

    @TestCaseAttribute
    private String priority;

    @TestCaseAttribute
    private String category;

    @TestCaseAttribute
    private String description;

    @TestCaseAttribute
    private String expectedResult;

    private String line;

    /**
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line
     *            the line to set
     */
    public void setLine(String line) {
        this.line = line;
    }

    private String actualResult;

    /**
     * @return the actualResult
     */
    public String getActualResult() {
        return actualResult;
    }

    /**
     * @param actualResult
     *            the actualResult to set
     */
    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    @TestCaseAttribute
    private String isIgnore;

    @TestCaseAttribute
    private String isFastFail;

    @TestCaseAttribute
    private String bugURL;

    private Result result;

    private String apiName;

    private String apiVersion;

    private String releaseVersion;

    private String owner;

    @TestCaseAttribute
    private String domainA;

    /**
     * @return the domainA
     */
    public String getDomainA() {
        return domainA;
    }

    /**
     * @param domainA
     *            the domainA to set
     */
    public void setDomainA(String domainA) {
        this.domainA = domainA;
    }

    /**
     * @return the domainB
     */
    public String getDomainB() {
        return domainB;
    }

    /**
     * @param domainB
     *            the domainB to set
     */
    public void setDomainB(String domainB) {
        this.domainB = domainB;
    }

    @TestCaseAttribute
    private String domainB;
}