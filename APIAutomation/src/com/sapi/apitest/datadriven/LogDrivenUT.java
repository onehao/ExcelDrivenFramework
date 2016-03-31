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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.sapi.apitest.datadriven.context.ApplicationContext;
import com.sapi.apitest.datadriven.entity.APIOutputPara;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;
import com.sapi.apitest.datadriven.entity.TestExecutionResult;
import com.sapi.apitest.datadriven.notificaitons.email.EmailSenderFactory;
import com.sapi.apitest.datadriven.notificaitons.email.EmailSenderType;
import com.sapi.apitest.datadriven.notificaitons.email.SimpleEmailSender;

/***
 * The Excel driven framework entrance.
 * 
 * @author wanhao01
 *
 */
public class LogDrivenUT {

	// used to store the test cases
	List<ExcelDrivenTestCase> cases = new LinkedList<ExcelDrivenTestCase>();

	//ExcelDrivenTestContext context;

	ExcelDrivenTestCaseExecutor executor;

	static final Logger logger = LogManager.getLogger(Thread.currentThread()
			.getStackTrace()[1].getClassName());

	private final String resultSummary = "Totally executed %d Testcases, Diff fail : %d，fail rate: %f%s\r\n\r\n";
	private final String resultExecutionTimeHtmlTemplate = "Totally executed time : %d seconds.\r\n\r\n";

	private final String emailProperty = "notification.email";

	private Properties properties;

	/**
	 * @return the cases
	 */
	public List<ExcelDrivenTestCase> getCases() {
		return cases;
	}

	public LogDrivenUT() {
//		this(System.getProperty("user.dir") + "/config/exceldriven.properties");
		this(LogDrivenUT.class.getResourceAsStream("/config/exceldriven.properties"));
	}

	public LogDrivenUT(InputStream propertiesLocation) {
		this.properties = new Properties();
		try {
			properties.load(propertiesLocation);
			//context = new ExcelDrivenTestContext(properties);
			executor = new ExcelDrivenTestCaseExecutor();
		} catch (FileNotFoundException e) {
			logger.error("{}\n{}", e.getMessage(), e.getStackTrace());
		} catch (IOException e) {
			logger.error("{}\n{}", e.getMessage(), e.getStackTrace());
		}
	}

	public void getAndExecuteTestCasesForDiff(
			List<ExcelDrivenTestCase> testcases, String module, String apiName, int threadCount) {
		this.cases = testcases;
		executeTestcasesForDiff(testcases, module, apiName,threadCount);
	}


	/**
	 * Execute test cases in the specific excel worksheet.
	 * 
	 * @param testcases
	 * @param sheetName
	 */
	public void executeTestcasesForDiff(List<ExcelDrivenTestCase> testcases,
			String module, String apiName, int threadCount) {
		StopWatch watch = new StopWatch();
		watch.start();
		String rulePath = String.format("%s_%s.testrules", module, apiName);
		if (!new File(rulePath).exists()) {
			generateCompareRuleFile(testcases, module, apiName, threadCount);
		}
		Map<String, APIOutputPara> rules = CompareStrategyUtil
				.readRules(rulePath);
		Calendar now = new GregorianCalendar(Locale.CHINA);
		String timestamp = String.format("%d-%d-%d-%d-%d-%d",
				now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY),
				now.get(Calendar.MINUTE), now.get(Calendar.SECOND));

		String logPath = String.format("%s_%s_%s.html", module, apiName,
				timestamp);
		List<List<TestExecutionResult>> result = this.executor.test(
				ApplicationContext.domainA,
				ApplicationContext.domainB, testcases, rules, threadCount);
		this.executor.saveResult(logPath, result, watch.getTime() / 1000);
		// sendEmail(logPath, result, watch.getTime() / 1000);
	}


	/**
	 * Sending email(s).
	 * 
	 * @param logPath
	 * @param result
	 */
	private void sendEmail(String logPath,
			List<List<TestExecutionResult>> result, long seconds) {
		List<String> emailAddress = Arrays.asList(this.properties.getProperty(
				emailProperty).split(";"));
		SimpleEmailSender sender = EmailSenderFactory
				.getSender(EmailSenderType.SERVICE);
		String text = String.format(Locale.CHINA, resultSummary, (this.cases.size()), result.get(0).size(),
				(float) result.get(0).size()
						/ (this.cases.size()) * 100,
				"%");
		text += String.format(Locale.CHINA, resultExecutionTimeHtmlTemplate,seconds);
		
		try {
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(text);

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			mbp2.attachFile(logPath);
			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			sender.send(emailAddress,
					logPath.substring(0, logPath.lastIndexOf(".")), mp);
		} catch (MessagingException | IOException e) {
			logger.error(e);
		}

	}

	/**
	 * Sending email(s).
	 * 
	 * @param logPath
	 * @param result
	 */
	public void sendEmail(Entry<String, Result> result) {
		List<String> emailAddress = Arrays.asList(this.properties.getProperty(
				emailProperty).split("[;,]"));
		SimpleEmailSender sender = EmailSenderFactory
				.getSender(EmailSenderType.SERVICE);
		StringBuilder text = new StringBuilder();
		text.append(String.format("Totally executed: %s, failed: %s\n\r\n",
				this.cases.size(), result.getValue()
						.getFailureCount()));
		for (Failure fail : result.getValue().getFailures()) {
			text.append(String.format("Test Case: %s \n", fail.getDescription()
					.getDisplayName()));
			text.append(String.format("Reason: %s \n", fail.getException()));
			text.append("------------------------------------------------------------------------\n");
		}

		try {
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(text.toString());

			// create the second message part
			// MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			// mbp2.attachFile(logPath);
			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			// mp.addBodyPart(mbp2);
			Calendar now = new GregorianCalendar(Locale.CHINA);
			String timestamp = String.format("%d-%d-%d-%d-%d-%d",
					now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
					now.get(Calendar.DAY_OF_MONTH),
					now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),
					now.get(Calendar.SECOND));

			sender.send(emailAddress, String.format("Test-%s_failed:[%d]__%s",
					result.getKey(), result.getValue().getFailureCount(),
					timestamp), mp);
		} catch (MessagingException e) {
			logger.error(e);
		}

	}

	/**
	 * Generate the key comparison level rule for the specific work sheet.
	 * 
	 * @param wb
	 * @param workbookName
	 * @param sheetName
	 */
	private void generateCompareRuleFile(List<ExcelDrivenTestCase> testcases, String module,
			String apiName, int threadCount) {
		CompareStrategyUtil.generatCompareRule(
				testcases,
				String.format("%s_%s.testrules", module,
						apiName), threadCount);
	}

	public static void main(String[] args) {
		Calendar now = new GregorianCalendar(Locale.CHINA);
		String timestamp = String.format("%d%d%d%d%d%d",
				now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY),
				now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
		System.out.println(timestamp);
		LogDrivenUT ut = new LogDrivenUT();
		// ut.generateCompareRuleFiles();
//		List<ExcelDrivenTestCase> testcases;
//		ut.getAndExecuteTestCasesForDiff();
	}
}