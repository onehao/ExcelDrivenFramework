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

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.sapi.apitest.datadriven.LogDrivenUT;
import com.sapi.apitest.datadriven.context.ApplicationContext;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase.RequestType;

/**
 * Test runner for excel driven framework.
 * 
 * @author wanhao01
 *
 */
public class LogDiffRunner {
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());
    
    public static void main(String[] args) throws FileNotFoundException {
    	LogDiffRunnerArgs runnerArgs = new LogDiffRunnerArgs();
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
        StringUtils.isEmpty("");
        ApplicationContext.domainA = runnerArgs.urlA;
        ApplicationContext.domainB = runnerArgs.urlB;
        List<ExcelDrivenTestCase> testcases = new ArrayList<ExcelDrivenTestCase>();
        String input = "";
        BufferedReader reader = new  BufferedReader(new FileReader(runnerArgs.logLocation));
        try {
        	int i = 0;
			while(!((input = reader.readLine()) == null)){
				if(input.length() == 0){
					continue;
				}
				ExcelDrivenTestCase test = new ExcelDrivenTestCase();
				test.setApiName(runnerArgs.apiName);
				input = input.replaceAll(",", "%2c");
				input = input.replaceAll(" ", "+");
				test.setTestURL(ApplicationContext.domainA + input);
				test.setRequestType("GET");
				test.setDescription("test -- " + i++);
				test.setApiName(runnerArgs.apiName);
				test.setDescription(runnerArgs.module);
				testcases.add(test);
				
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        LogDrivenUT ut = new LogDrivenUT();
        ut.getAndExecuteTestCasesForDiff(testcases, runnerArgs.module, runnerArgs.apiName, runnerArgs.threadCount);

    }
}