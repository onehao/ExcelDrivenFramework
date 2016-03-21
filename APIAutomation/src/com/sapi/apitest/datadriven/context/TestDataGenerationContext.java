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
package com.sapi.apitest.datadriven.context;

import java.util.Properties;

/***
 * The respected service info that used to exporting the test data,especially
 * for the geosearch&geodata.
 * 
 * @author wanhao01
 * 
 *
 */
public class TestDataGenerationContext {

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestContext() {
        return requestContext;
    }

    public String getRequestType() {
        return requestType;
    }

    private String requestUrl;

    private String requestContext;

    private String requestType;

    private Properties properties;

    public TestDataGenerationContext(Properties properties) {
        this.properties = properties;
        init();
    }

    public TestDataGenerationContext() {
        init();
    }

    /**
     * Initialized the context values.
     */
    private void init() {
        this.requestUrl = this.properties.getProperty(
                "testdata.gen.requestUrl", "");
        this.requestContext = this.properties.getProperty(
                "testdata.gen.requestContext", "");
        this.requestType = this.properties.getProperty(
                "testdata.gen.requestType", "");
    }

    /**
     * Load the specific properties.
     * @param properties
     */
    public void loadProperties(Properties properties) {
        this.properties = properties;
    }
}