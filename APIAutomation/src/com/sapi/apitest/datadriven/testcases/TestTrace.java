package com.sapi.apitest.datadriven.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sapi.apitest.datadriven.TestBase;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase.RequestType;

public class TestTrace extends TestBase {
	static final Logger logger = LogManager.getLogger(Thread.currentThread()
			.getStackTrace()[1].getClassName());

	final static String hostname = "http://api.map.baidu.com";

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
		this.httpclient.close();
		response = null;
	}

	/**
	 * Check for headers. FOR Geosearch bound
	 */
	@Test()
	public void testBoundHeaders() {
		String url = hostname +  "/geosearch/v3/bound?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&sortby=distance%3a1&bounds=116.350658,39.901873;116.446238,39.961848";
		response = this.getHttpResponse(RequestType.GET, url);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("application/json;charset=utf-8", response
				.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

}
