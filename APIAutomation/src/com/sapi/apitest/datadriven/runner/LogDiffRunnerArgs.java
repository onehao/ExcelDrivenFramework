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
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class LogDiffRunnerArgs {
	@Option(name = "-module", usage = "the module specified to be run, current support:"
            + "sns, maps, amap, case insensitive")
    String module;
    @Option(name = "-apiName", usage = "the api name specified to be run, current support")
    String apiName;
    
    @Option(name = "-logLocation", usage = "the locaiton of the log file.")
    String logLocation;
    
    @Option(name = "-threadCount", usage = "the thread number being used to run the test cases.")
    int threadCount = 1;
    
    @Option(required = true, name = "-urlA", usage = "the url must be speified. when urlB isn't specicied, "
            + "the urlA is used to do comparison with expected result.")
    String urlA;
    @Option(name = "-urlB", usage = "using when doing online and offline comparison, "
            + "when urlB is specified , urlA must be specified either. ")
    String urlB;
    
    // receives other command line parameters than options  
    @Argument  
    List<String> arguments = new ArrayList<String>(); 
}