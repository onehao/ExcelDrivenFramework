package com.sapi.apitest.datadriven.testcases;

import static com.eclipsesource.restfuse.Assert.assertOk;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.annotation.Poll;
import com.sapi.apitest.datadriven.TestBase;

@RunWith(HttpJUnitRunner.class)
public class TestRESTSample extends TestBase {

	@Rule
	public Destination restfuse = new Destination(this, this.domain);

	@Context
	private Response restResp;

	@Context
	private PollState pollState;

	@HttpTest(method = Method.GET, path = "/geosearch/v3/bound?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&sortby=distance%3a1&bounds=116.350658,39.901873;116.446238,39.961848&")
	public void checkRestfuseOnlineStatus() {
		assertOk(restResp);
	}

	/**
	 * Sample check body. Body check may use regex, contains or any other check
	 * point you need. if you want to check json output key-value pairs, refer
	 * to the sample testOutputKeyValue.
	 */
	@HttpTest(method = Method.GET, path = "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0")
	public void checkBody() {
		String expectedResult = "{\"status\":0, \"total\":1, \"size\":1, \"contents\":[{\"category\":\"\\u516C\\u4EA4\", \"category_en\":\"bus\", \"uid\":934137560, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"type2\":2, \"geotable_id\":106990, \"district\":\"\\u4E1C\\u57CE\\u533A\", \"create_time\":1434080106, \"metrotype\":1, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"location\":[116.40434999999999, 39.939799999999998], \"url\":\"http:\\/\\/bus.bj.bendibao.com\\/zhandian\\/station289003.htm\", \"title\":\"\\u5730\\u5B89\\u95E8\\u4E1C\", \"coord_type\":3, \"type\":0, \"distance\":133, \"weight\":0}]}";
		assertEquals(expectedResult, restResp.getBody());
	}

	/**
	 * Check for headers. FOR Geosearch local
	 */
	@HttpTest(method = Method.GET, path = "/geosearch/v3/local?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&sortby=type2:-1")
	public void testLocalHeaders() {
		Map<String, List<String>> headers = restResp.getHeaders();
		assertOk(restResp);
		assertEquals("application/json;charset=utf-8",
				headers.get("Content-Type").get(0));
		assertEquals("0", headers.get("Error-Code").get(0));
		assertEquals("apache", headers.get("Server").get(0));
		assertEquals("apimap", headers.get("Http_x_bd_subsys").get(0));
		assertEquals("map", headers.get("Http_x_bd_product").get(0));
		assertTrue(headers.get("Http_x_bd_logid64").size() > 0);
		assertTrue(headers.get("Http_x_bd_logid").size() > 0);
	}

	/**
	 * Sample to check the outout key-value. Currently only support json and
	 * xml. for other format, you may need to wapper method to handle body from
	 * restResp.
	 * 
	 */
	@HttpTest(method = Method.GET, path = "/geosearch/v3/local?region=%E5%8C%97%E4%BA%AC&ak=6dfdb2d835c6673ae359c796bba28197&geotable_id=63871&page_size=50&page_index=40")
	public void testLocalUserScenario() {
		Map<String, String> map = this.responseToMap(restResp);
		assertEquals("0", map.get("status"));
		assertEquals("3079", map.get("total"));
		assertEquals("50", map.get("size"));
		assertEquals("50", map.get("contents#"));
	}

	/**
	 * Sample poll test, we may used this when test functional check point in
	 * unstable env. Also, when dealing with test scenario like CURD, we may
	 * want delete operation to be performed successfully with several attempts.
	 */
	@HttpTest(method = Method.GET, path = "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0")
	@Poll(times = 5, interval = 500)
	public void testAsynchronousService() {
		Response currentResponse = pollState.getResponse(pollState.getTimes());
		System.out.println("Attemt " + pollState.getTimes());
		System.out.println(pollState.getTimes() + ". Responsecode = "
				+ pollState.getResponse(pollState.getTimes()).getStatus());
		assertEquals(currentResponse, restResp);
	}
}
