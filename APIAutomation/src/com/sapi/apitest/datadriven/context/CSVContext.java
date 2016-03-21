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
 * The csv file stored the requests parsing from the web log, and we use the data
 * to create the init test data especially for geosearch.
 * 
 * @author wanhao01 
 * 
 *
 */
public class CSVContext {

    public enum Mode {
        FILE, WEB
    }

    private String inputLocation;

    public String getInputLocation() {
        return inputLocation;
    }

    public String getOutputLocation() {
        return outputLocation;
    }

    public Mode getMode() {
        return mode;
    }

    private String outputLocation;

    private Mode mode;

    private Properties properties;

    private String encoding;
    
    public String getEncoding() {
        return encoding;
    }

    public CSVContext(Properties properties) {
        this.properties = properties;
        init();
    }

    public CSVContext() {
        init();
    }

    /**
     * Load the specific properties.
     * @param properties
     */
    public void loadPropertes(Properties properties) {
        this.properties = properties;
    }

    /**
     * Initialize the properties and getting parameters.
     */
    private void init() {
        this.inputLocation = this.properties.getProperty("csv.input", "");
        this.outputLocation = this.properties.getProperty("csv.output", "");
        this.encoding = this.properties.getProperty("csv.encodeing", "");
        if (this.properties.getProperty("csv.mode", "").equals("file")) {
            this.mode = Mode.FILE;
        } else {
            this.mode = Mode.WEB;
        }
    }
}