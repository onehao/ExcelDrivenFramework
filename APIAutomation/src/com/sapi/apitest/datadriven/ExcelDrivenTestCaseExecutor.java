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
package com.sapi.apitest.datadriven;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.concurrency.Comparator;
import com.sapi.apitest.datadriven.concurrency.Comparator.ComparatorResult;
import com.sapi.apitest.datadriven.concurrency.ThreadManager;
import com.sapi.apitest.datadriven.entity.APIOutputPara;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;
import com.sapi.apitest.datadriven.entity.TestExecutionResult;
import com.sapi.apitest.datadriven.template.Template;
import com.sapi.apitest.datadriven.template.TemplateImpl;

/***
 * The excel driven automation framework that used to execute and report the
 * result.
 * 
 * @author wanhao01
 * 
 *
 */
public class ExcelDrivenTestCaseExecutor {

	static final Logger logger = LogManager.getLogger(Thread.currentThread()
			.getStackTrace()[1].getClassName());

	// The following section defined the tempalte structure of the reporting.
	private final String noResult = "<h2>all execution test passed.</h2>";
	private final String resultSummaryHtmlTemplate = "<h2>Totally executed %d Testcases, Diff fail :"
			+ " %d, fail rate: %f%s</h2><br />";
	private final String resultExecutionTimeHtmlTemplate = "<h2>Totally executed time : %d seconds</h2><br />";
	private final String failedHeaderHtmlTemplate = "<h3 style=\"color:#FF0000\">failed test cases list: </h3>";
	private final String passedHeaderHtmlTemplate = "<h3 style=\"color:#00FF00\">passed test cases list: </h3>";
	private final String compareDetailHtmlTemplate = "compare details : %s\r\n\r\n<br/>";
	private final String url0HtmlTemplate = "url0 : <a href=\"%s\">%s</a>\r\n<br/>";
	private final String url1HtmlTemplate = "url1 : <a href=\"%s\">%s</a>\r\n<br/>";
	private final String testTitleHtmlTemplate = "<div class=\"toggler-1\" title=\"%s: %s, testcase: %s \"><br />";
	private final String endDivHtmlTemplate = "</div>";

	private static final String PASS = "PASS";
	private static final String FAIL = "FAIL";
	
//	public static AtomicInteger count  = new AtomicInteger();
//	public static AtomicInteger failedCount  = new AtomicInteger();
	
	public static int count  = 0;
	public static int failedCount  = 0;

	// used to store the test cases
	List<ExcelDrivenTestCase> cases = new LinkedList<ExcelDrivenTestCase>();

	/**
	 * Execute the test cases.
	 * 
	 * @param domain0
	 *            , the expected result url.
	 * @param domain1
	 *            , the actual result url.
	 * @param testcases
	 * @param rules
	 * @param threadCount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<TestExecutionResult>> test(String domain0, String domain1,
			List<ExcelDrivenTestCase> testcases,
			Map<String, APIOutputPara> rules, int threadCount) {
		int block = testcases.size() / threadCount;
		this.cases = testcases;
		List<Callable<ComparatorResult>> comparators = new ArrayList<Callable<ComparatorResult>>();
		for (int i = 0; i < threadCount; i++) {
			Comparator comparator = new Comparator();

			// as for regression test, we usually used the same online url
			// for all the test cases, and same offline url for all the
			// cases either, so we don't set the domainA and domainB for
			// each test case in this case, and set the urls on the
			// comparator level.
			comparator.setDomain0(domain0);
			comparator.setDomain1(domain1);

			Map<String, APIOutputPara> rulesCopy = new HashMap<String, APIOutputPara>();
			for (String s : rules.keySet()) {
				rulesCopy.put(s, rules.get(s));
			}
			comparator.setRules(rulesCopy);
			if (i == threadCount - 1) {
				comparator.setTestCases(testcases.subList(i * block,
						testcases.size()));
			} else {
				comparator.setTestCases(testcases.subList(i * block, i * block
						+ block));
			}
			comparators.add(comparator);
		}
		// testcases.clear();
		// List<Thread> threadPool = ThreadManager.getThreadPool(comparators);
		// ThreadManager.startThreadPool(threadPool);
		// ThreadManager.joinThreadPool(threadPool);
		List<Future<ComparatorResult>> results = ThreadManager
				.submitThreadPool(comparators);
		List<List<TestExecutionResult>> result = new ArrayList<List<TestExecutionResult>>();
		List<TestExecutionResult> passResult = new ArrayList<TestExecutionResult>();
		List<TestExecutionResult> failResult = new ArrayList<TestExecutionResult>();
		result.add(failResult);
		result.add(passResult);

		for (Future<ComparatorResult> r : results) {
			try {
				failResult.addAll(r.get().getFailCase());
				// TODO: passResult.addAll(r.get().getPassCase());
			} catch (InterruptedException e) {
				logger.error(e);
			} catch (ExecutionException e) {
				logger.error(e);
			}
		}
		// for (Callable<ComparatorResult> thread : comparators) {
		// Comparator temp = (Comparator) thread;
		// passResult.addAll(temp.getPassCase());
		// failResult.addAll(temp.getFailCase());
		// }
		return result;
	}

	/**
	 * Save the test result back to the excel file.
	 * 
	 * @param savePath
	 * @param result
	 */
	public void saveResult(String savePath,
			List<List<TestExecutionResult>> result, long seconds) {
		OutputStreamWriter writer;

		try {
			writer = new OutputStreamWriter(new FileOutputStream(new File(
					savePath)), "utf8");
			BufferedWriter bwriter = new BufferedWriter(writer);
			String content = resultFormat(result, seconds);
			Template resultContent = new TemplateImpl();
			resultContent.getReport(bwriter, content);
			bwriter.close();
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Format and return the result.
	 * 
	 * @param result
	 * @return the result content.
	 * @throws IOException
	 */
	private String resultFormat(List<List<TestExecutionResult>> result, long seconds)
			throws IOException {
		StringBuilder content = new StringBuilder();
		if (result == null || result.size() != 2
				|| result.get(0).size() + result.get(1).size() == 0) {
			content.append(noResult);

			return content.toString();
		}
		List<TestExecutionResult> failResult = result.get(0);
		List<TestExecutionResult> passResult = result.get(1);
//		content.append(String.format(Locale.CHINA, resultSummaryHtmlTemplate,
//				(result.get(0).size() + result.get(1).size()), result.get(0)
//						.size(), (float) result.get(0).size()
//						/ (result.get(0).size() + result.get(1).size()) * 100,
//				"%"));
		content.append(String.format(Locale.CHINA, resultSummaryHtmlTemplate,
				(this.cases.size()), result.get(0)
						.size(), (float) result.get(0).size()
						/ (this.cases.size()) * 100,
				"%"));
		content.append(String.format(Locale.CHINA, resultExecutionTimeHtmlTemplate, seconds));
		// content.append("---------------------------------------------------------------------------------------\r\n");
		content.append(failedHeaderHtmlTemplate);
		int count = 1;
		for (TestExecutionResult r : failResult) {
			content.append(String.format(testTitleHtmlTemplate, FAIL, count++,
					r.getTestDescription()));
			content.append(String.format(url0HtmlTemplate, r.getUrl0(),
					r.getUrl0()));
			content.append(String.format(url1HtmlTemplate, r.getUrl1(),
					r.getUrl1()));
			content.append(String.format(compareDetailHtmlTemplate,
					r.getCompDetail()));
			content.append(endDivHtmlTemplate);
		}
		// content.append("\r\n\r\n---------------------------------------------------------------------------------------\r\n\r\n");
		// count = 1;
		// content.append(passedHeaderHtmlTemplate);
		// for (TestExecutionResult r : passResult) {
		// content.append(String.format(testTitleHtmlTemplate, PASS, count++,
		// r.getTestDescription()));
		// content.append(String.format(url0HtmlTemplate, r.getUrl0(),
		// r.getUrl0()));
		// content.append(String.format(url1HtmlTemplate, r.getUrl1(),
		// r.getUrl1()));
		// content.append(String.format(compareDetailHtmlTemplate,
		// r.getCompDetail()));
		// content.append(endDivHtmlTemplate);
		// }
		return content.toString();
	}
}