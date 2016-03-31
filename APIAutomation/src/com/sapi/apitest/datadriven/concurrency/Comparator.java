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
package com.sapi.apitest.datadriven.concurrency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapi.apitest.datadriven.CompareStrategyUtil;
import com.sapi.apitest.datadriven.ExcelDrivenTestCaseExecutor;
import com.sapi.apitest.datadriven.comparison.ComparisonEngine;
import com.sapi.apitest.datadriven.concurrency.Comparator.ComparatorResult;
import com.sapi.apitest.datadriven.entity.APIOutputPara;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;
import com.sapi.apitest.datadriven.entity.TestExecutionResult;
import com.sapi.apitest.datadriven.utils.HttpRequest;
import com.sapi.apitest.datadriven.utils.XmlAndJsonUtil;

/**
 * The thread used to execute test cases in concurrency.
 * 
 * @author wanhao01
 * 
 */
public class Comparator implements Callable<ComparatorResult> {

	private static final int HTTP_CONTEXT_INDEX = 7;

	private static final int HTTPS_CONTEXT_INDEX = 8;

	static final Logger logger = LogManager.getLogger(Thread.currentThread()
			.getStackTrace()[1].getClassName());

	private static final String PASS = "PASS";
	private static final String FAIL = "FAIL";
	private static final String TEST_CASE_EXECUTION_TEMPLATE = "%s, test - %d failed - %d,  Testcase:%s_%s_%s";
	private final String resultError = "exception when executing the testase with the description %s.";
	private final String assertionFailedORNotSupportKey = "assertion failed or not supported";
	private final String assertionFailedOrNotSupportValue = "(assertion failed or not supported-%s)";
	private final String requestFailedKey = "request failed";
	private final String requestFailedValue = "(error when sending %s request-%s)";
	private final String logExecutionError = "error when executing the testcase with the url {} , "
			+ "urlA: {}, urlB: {}. on the iterataion {}.";

	private String domain0;
	private String domain1;
	private List<ExcelDrivenTestCase> testcases = new ArrayList<ExcelDrivenTestCase>();
	private Map<String, APIOutputPara> rules = new HashMap<String, APIOutputPara>();
	private List<TestExecutionResult> failCase = new ArrayList<TestExecutionResult>();
	private List<TestExecutionResult> passCase = new ArrayList<TestExecutionResult>();

	/**
	 * The class defined the structure of the test case result.
	 * 
	 * @author wanhao01
	 *
	 */
	public static class ComparatorResult {
		List<TestExecutionResult> failCase;

		/**
		 * @return the failCase
		 */
		public List<TestExecutionResult> getFailCase() {
			return failCase;
		}

		/**
		 * @param failCase
		 *            the failCase to set
		 */
		public void setFailCase(List<TestExecutionResult> failCase) {
			this.failCase = failCase;
		}

		/**
		 * @return the passCase
		 */
		public List<TestExecutionResult> getPassCase() {
			return passCase;
		}

		/**
		 * @param passCase
		 *            the passCase to set
		 */
		public void setPassCase(List<TestExecutionResult> passCase) {
			this.passCase = passCase;
		}

		List<TestExecutionResult> passCase;

		public ComparatorResult(List<TestExecutionResult> failCase,
				List<TestExecutionResult> passCase) {
			this.failCase = failCase;
			this.passCase = passCase;
		}
	}

	public String getDomain0() {
		return domain0;
	}

	public void setDomain0(String domain0) {
		this.domain0 = domain0;
	}

	public String getDomain1() {
		return domain1;
	}

	public void setDomain1(String domain1) {
		this.domain1 = domain1;
	}

	public List<ExcelDrivenTestCase> getTestCases() {
		return testcases;
	}

	public void setTestCases(List<ExcelDrivenTestCase> testcases) {
		this.testcases = testcases;
	}

	public Map<String, APIOutputPara> getRules() {
		return rules;
	}

	public void setRules(Map<String, APIOutputPara> rules) {
		this.rules = rules;
	}

	public List<TestExecutionResult> getFailCase() {
		return failCase;
	}

	public void setFailCase(List<TestExecutionResult> failCase) {
		this.failCase = failCase;
	}

	public List<TestExecutionResult> getPassCase() {
		return passCase;
	}

	public void setPassCase(List<TestExecutionResult> passCase) {
		this.passCase = passCase;
	}

	/**
	 * Execute single test case, do another request on failure which may cased
	 * by the offline env not stable. focus on regression and ignore stability.
	 * 
	 * @param urlA
	 *            : the online url.
	 * @param urlB
	 *            : the offline url.
	 * @param testcase
	 * @param compResultsMap
	 *            :the result to be set to.
	 * @return the list of execution results.
	 */
	private List<String> tryExecute(String urlA, String urlB,
			ExcelDrivenTestCase testcase, Map<String, String> compResultsMap) {
		List<String> resultSet = new ArrayList<String>();
		String resultStatus = "";
		String resultString0 = "";
		String resultString1 = "";
		boolean retry = true;
		Throwable throwable = null;
		for (int i = 0; i < 2 && retry == true; i++) {
			resultStatus = PASS;
			resultString0 = HttpRequest.sendRequest(testcase.getRequestType(),
					urlA);
			if (urlB == null || urlB.isEmpty()) {
				resultString1 = testcase.getExpectedResult();
			} else {
				resultString1 = HttpRequest.sendRequest(
						testcase.getRequestType(), urlB);
			}
			try {
				if (XmlAndJsonUtil.isJsonString(resultString0)
						&& XmlAndJsonUtil.isJsonString(resultString1)) {
					resultStatus = compareKeyInTwoObject(compResultsMap,
							resultString0, resultString1);
					retry = false;
					break;
				} else if (XmlAndJsonUtil.isXML(resultString0)
						&& XmlAndJsonUtil.isXML(resultString1)) {
					resultStatus = compareKeyInTwoObject(compResultsMap,
							XmlAndJsonUtil.xml2JSON(resultString0, true),
							XmlAndJsonUtil.xml2JSON(resultString1, true));
					retry = false;
					break;
				} else {
					if (resultString0.contains(HttpRequest.ERROR_PREFIX)
							|| resultString1.contains(HttpRequest.ERROR_PREFIX)) {
						compResultsMap.put(
								requestFailedKey,
								String.format(requestFailedValue,
										testcase.getRequestType(), FAIL));
						retry = true;
					} else {
						compResultsMap.put(assertionFailedORNotSupportKey,
								String.format(assertionFailedOrNotSupportValue,
										FAIL));
						retry = false;
					}

					resultStatus = FAIL;
				}
			} catch (JsonParseException e) {
				throwable = e;
			} catch (JsonMappingException e) {
				throwable = e;
			} catch (IOException e) {
				throwable = e;
			} catch (Exception e) {
				throwable = e;
			} finally {
				if (null != throwable) {
					logger.error(logExecutionError, testcase.getTestURL(),
							urlA, urlB, i);
					logger.error(throwable);
					resultStatus = FAIL;
					compResultsMap.put(
							throwable.toString(),
							String.format(resultError,
									testcase.getDescription()));
					retry = true;
				}
			}
		}

		resultSet.add(resultStatus);
		resultSet.add(resultString0);
		resultSet.add(resultString1);

//		logger.info(String
//				.format(TEST_CASE_EXECUTION_TEMPLATE,
//						resultStatus,
//						ExcelDrivenTestCaseExecutor.count.incrementAndGet(),
//						resultStatus.equals(FAIL) ? ExcelDrivenTestCaseExecutor.failedCount
//								.incrementAndGet()
//								: ExcelDrivenTestCaseExecutor.failedCount.get(),
//						testcase.getLine(), testcase.getCategory(), testcase
//								.getDescription()));
		
		logger.info(String
				.format(TEST_CASE_EXECUTION_TEMPLATE,
						resultStatus,
						ExcelDrivenTestCaseExecutor.count++,
						resultStatus.equals(FAIL) ? ExcelDrivenTestCaseExecutor.failedCount++
								: ExcelDrivenTestCaseExecutor.failedCount,
						testcase.getLine(), testcase.getCategory(), testcase
								.getDescription()));
		
		if (resultStatus.equals(FAIL)) {
			logger.info(compResultsMap);
		}
		testcase = null;
		return resultSet;
	}

	/**
	 * Compare two json entity and set comparison result.
	 * 
	 * @param compResultsMap
	 * @param resultString0
	 * @param resultString1
	 * @return
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws Exception
	 */
	private String compareKeyInTwoObject(Map<String, String> compResultsMap,
			String resultString0, String resultString1) throws IOException,
			JsonParseException, JsonMappingException, Exception {
		String resultStatus;
		ObjectMapper mapper = new ObjectMapper();
		Object o0 = mapper.readValue(resultString0, Object.class);
		Object o1 = mapper.readValue(resultString1, Object.class);
		resultStatus = ComparisonEngine.compareKeysInTwoObjects(rules, o0, o1,
				compResultsMap);
		return resultStatus;
	}

	/**
	 * Execute all the test cases in one work sheet.
	 */
	private void execute() {
		String rexString = "";
		for (String s : rules.keySet()) {
			if (s.startsWith(CompareStrategyUtil.STRING_REGEX)) {
				rexString = s.substring(12);
			}
		}
		for (ExcelDrivenTestCase testcase : testcases) {
			TestExecutionResult result = new TestExecutionResult();
			result.setTestDescription(String.format("%s_%s_%s",
					testcase.getLine(), testcase.getCategory(),
					testcase.getDescription()));
			if (testcase.getTestURL() == null
					|| testcase.getTestURL().length() <= 0) {
				String error = String
						.format(" the url of the the testcase \"%s\""
								+ " on the line %s is null, please check the testcase.",
								testcase.getDescription(), testcase.getLine());
				logger.error(error);
				result.setCompState(FAIL);
				result.setCompDetail(error);
				failCase.add(result);
				continue;
			}

			String urlA = getUrl(domain0, testcase);
			String urlB = (domain1 == null || domain1.isEmpty()) ? "" : getUrl(
					domain1, testcase);
			result.setUrl0(urlA);
			result.setUrl1(urlB);

			String comparaStatus = PASS;
			Map<String, String> compResultsMap = new LinkedHashMap<>();
			try {

				List<String> resultlist = tryExecute(urlA, urlB, testcase,
						compResultsMap);
				comparaStatus = resultlist.get(0);

				if (rules.containsKey(String.format("%s=true",
						CompareStrategyUtil.ENTIRE_STRING_EQUALS))) {
					if (!resultlist.get(1).equals(resultlist.get(2))) {
						comparaStatus = FAIL;
						compResultsMap
								.put(CompareStrategyUtil.ENTIRE_STRING_EQUALS,
										String.format(
												"(%s-%s)",
												CompareStrategyUtil.ENTIRE_STRING_EQUALS,
												FAIL));
					} else {
						compResultsMap
								.put(CompareStrategyUtil.ENTIRE_STRING_EQUALS,
										String.format(
												"(%s-%s)",
												CompareStrategyUtil.ENTIRE_STRING_EQUALS,
												PASS));
					}
				}
				if (!rexString.trim().equals("")) {
					// Pattern pattern = Pattern.compile(rexString);
					// Matcher matcher0 = pattern.matcher(resultString0.);
					// Matcher matcher1 = pattern.matcher(resultString1);
					if (resultlist.get(1).matches(rexString)
							&& resultlist.get(2).matches(rexString)) {
						compResultsMap
								.put(CompareStrategyUtil.STRING_REGEX,
										String.format(
												"(%s-%s)",
												CompareStrategyUtil.STRING_REGEX,
												PASS));
					} else {
						comparaStatus = FAIL;
						compResultsMap
								.put(CompareStrategyUtil.STRING_REGEX,
										String.format(
												"(%s-%s)",
												CompareStrategyUtil.STRING_REGEX,
												FAIL));
					}
				}
				result.setCompState(comparaStatus);
				result.setCompDetail(convertMapToString(compResultsMap));
				resultlist.clear();
				resultlist = null;

			} catch (Exception e) {
				e.printStackTrace();
				result.setCompState(FAIL);
				result.setCompDetail(e.toString());
			} finally {
				compResultsMap.clear();
				compResultsMap = null;
			}
			if (result.getCompState().equals(PASS)) {
				// TODO: passCase.add(result);
			} else {
				failCase.add(result);
			}
		}
		// testcases.clear();
	}

	/**
	 * For the following two cases a. http://url/context/ect b. url/context/ect
	 * replace the url with the domain.
	 * 
	 * @param domain
	 * @param testcase
	 * @return
	 */
	private String getUrl(String domain, ExcelDrivenTestCase testcase) {
		StringBuilder oriUrl = new StringBuilder(testcase.getTestURL());
		String url = "";
		// ori url that start with http.
		int start = -1;
		if (testcase.getTestURL().toUpperCase().startsWith("HTTP")) {
			try {
				if (!testcase.getTestURL().toUpperCase().startsWith("HTTPS")) {
					// https://
					start = oriUrl.indexOf("/", HTTPS_CONTEXT_INDEX);
					url = oriUrl.substring(start);
				} else {
					// http://
					start = oriUrl.indexOf("/", HTTP_CONTEXT_INDEX);
					url = oriUrl.substring(start);
				}
			} catch (Exception e) {
				logger.error(e);
			}

		} else {
			// request not start with http
			try {
				url = oriUrl.substring(oriUrl.indexOf("/"));
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return String.format("http://%s%s", domain, url);
	}

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder("http://url/context/ect");
		int start = builder.indexOf("/", 7);
		String url = builder.substring(start + 1);
		System.out.println(url);
		builder = new StringBuilder("url/context/ect");
		url = builder.substring(builder.indexOf("/") + 1);
		System.out.println(url);
	}

	/**
	 * Convert the map object to a readable string.
	 * 
	 * @param compResultsMap
	 * @return
	 */
	public static String convertMapToString(Map<String, String> compResultsMap) {
		StringBuffer outputStr = new StringBuffer(100);
		if (compResultsMap != null && !compResultsMap.isEmpty()) {
			for (Map.Entry<String, String> compResultItem : compResultsMap
					.entrySet()) {
				outputStr.append(compResultItem.getValue());
			}
		}
		return outputStr.toString();
	}

	// @Override
	// public void run() {
	// if (domain0 == null || domain1 == null || testcases.size() == 0) {
	// return;
	// }
	// execute();
	// }

	/**
	 * The implementation used to execute the test cases, and return the result
	 * of execution.
	 */
	@Override
	public ComparatorResult call() throws Exception {
		if (domain0 == null || domain1 == null || testcases.size() == 0) {
			return null;
		}
		execute();
		return new ComparatorResult(failCase, passCase);
	}
}