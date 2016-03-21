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

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.sapi.apitest.datadriven.ExcelDrivenUT;
import com.sapi.apitest.datadriven.context.ApplicationContext;
import com.sapi.apitest.datadriven.testcases.TestRESTSample;
import com.sapi.apitest.datadriven.testcases.TestWebAPIConsistency;

import static org.kohsuke.args4j.ExampleMode.ALL; 


/**
 * Test runner for excel driven framework.
 * 
 * @author wanhao01
 *
 */
public class SampleTestSuiteRunner {
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
            .getStackTrace()[1].getClassName());
    
    public static void main(String[] args) {
        RunnerArgs runnerArgs = new RunnerArgs();
        CmdLineParser parser = new CmdLineParser(runnerArgs);
        try {  
            // parse the arguments.  
            parser.parseArgument(args);  
  
            // you can parse additional arguments if you want.  
            // parser.parseArgument("more","args");  
 
  
        } catch( CmdLineException e ) {  
            // if there's a problem in the command line,  
            // you'll get this exception. this will report  
            // an error message.  
            System.err.println(e.getMessage());  
            System.err.println("java -jar testrunner.jar [options...] arguments...");  
            // print the list of available options  
            parser.printUsage(System.err);  
            System.err.println();  
  
            // print option sample. This is useful some time  
            System.err.println(" Example: java -jar testrunner.jar"+parser.printExample(ALL));  
  
            return;  
        }  
        ApplicationContext.domainA = runnerArgs.urlA;
        ApplicationContext.domainB = runnerArgs.urlB;
    
        ExcelDrivenUT ut = new ExcelDrivenUT();
        // List<Result> results = new LinkedList<Result>();
        HashMap<String, Result> results = new HashMap<String, Result>();
        
        results.put(
                "Sample test class",
                JUnitCore.runClasses(TestRESTSample.class));

        for (Entry<String, Result> result : results.entrySet()) {
            ut.sendEmail(result);
        }
    }
}