package com.sapi.apitest.datadriven.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sapi.apitest.datadriven.ExcelDrivenUT;
import com.sapi.apitest.datadriven.TestBase;
import com.sapi.apitest.datadriven.annotations.UseCase;

public class TestGeoSearchExcelDriven extends TestBase{

	static final Logger logger = LogManager.getLogger(Thread.currentThread()
			.getStackTrace()[1].getClassName());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@UseCase(description = "fast fail", isFastFail = false)
	public void test() {
		ExcelDrivenUT ut = new ExcelDrivenUT();
		ut.getAndExecuteTestCases();
	}

}
