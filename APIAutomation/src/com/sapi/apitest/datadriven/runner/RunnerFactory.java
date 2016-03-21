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
package com.sapi.apitest.datadriven.runner;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunnerFactory {
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());
    
    private static HashMap<String, String[]> classMap = new HashMap<String, String[]>();
    
    static {
        classMap.put(
                "GeoTestSuite".toUpperCase(),
                new String[] { "com.sapi.apitest.datadriven.testcases.GeoTestSuite" });
        classMap.put(
                "GEOSEARCH".toUpperCase(),
                new String[] {
                    "com.sapi.apitest.datadriven.testcases.TestGeosearch",
                    "com.sapi.apitest.datadriven.testcases.TestGeoSearchExcelDriven" });
        classMap.put(
                "GEODATA".toUpperCase(),
                new String[] { "com.sapi.apitest.datadriven.testcases.TestGeodata" });
        classMap.put(
                "weiapi".toUpperCase(),
                new String[] { "com.sapi.apitest.datadriven.testcases.TestWebAPIConsistency" });
    }

    /**
     * Create runner class.
     * @param className
     * @return
     */
    public static Class<?>[] createRunnerClass(String className) {
        if (!classMap.containsKey(className.toUpperCase())) {
            return null;
        }
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        Class<?>[] classes = new Class<?>[classMap.get(className.toUpperCase()).length];
        int i = 0;
        for (String cName : classMap.get(className.toUpperCase())) {
            try {
                classes[i++] = Class.forName(cName);
            } catch (ClassNotFoundException e) {
                logger.error("The class module couldn't be found.");
            }
        }

        return classes;
    }
}