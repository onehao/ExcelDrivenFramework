package com.sapi.apitest.datadriven.testcases;

import org.apache.http.HttpResponse;
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

public class TestWebAPIConsistency extends TestBase {
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
		this.httpclient.close();
		response = null;
	}

	/**
	 * Check for headers. FOR place - search.
	 */
	@Test()
	public void testPlaceSearchJsonHeaders() {
		String url = this.domain
				+ "/place/v2/search?query=银行&region=济南&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8", response
				.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * Check for headers. FOR place - search xml format.
	 */
	@Test()
	public void testPlaceSearchXMLHeaders() {
		String url = this.domain
				+ "/place/v2/search?query=银行&region=济南&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * test place search for status line..
	 */
	@Test
	public void testPlaceSearchXMLStatusLine() {
		String url = this.domain
				+ "/place/v2/search?query=银行&region=济南&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * test place search for status line..
	 */
	@Test
	public void testPlaceJsonStatusLine() {
		String url = this.domain
				+ "/place/v2/search?query=银行&region=济南&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR place - detail.
	 */
	@Test()
	public void testPlaceDetailJsonHeaders() {
		String url = this.domain
				+ "/place/v2/detail?uid=5a8fb739999a70a54207c130&ak=0h8SLfsqRWcZWXiM1iUu2iZk&output=json&scope=2";
		response = this.getHttpResponse(RequestType.GET, url);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8", response
				.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * Check for headers. FOR place - detail xml format.
	 */
	@Test()
	public void testPlaceDetailXMLHeaders() {
		String url = this.domain
				+ "/place/v2/detail?uid=5a8fb739999a70a54207c130&ak=0h8SLfsqRWcZWXiM1iUu2iZk&output=xml&scope=2";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * test place detail for status line..
	 */
	@Test
	public void testPlaceDetailXMLStatusLine() {
		String url = this.domain
				+ "/place/v2/detail?uid=5a8fb739999a70a54207c130&ak=0h8SLfsqRWcZWXiM1iUu2iZk&output=xml&scope=2";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * test place detail for status line..
	 */
	@Test
	public void testPlaceDetailJsonStatusLine() {
		String url = this.domain
				+ "/place/v2/detail?uid=5a8fb739999a70a54207c130&ak=0h8SLfsqRWcZWXiM1iUu2iZk&output=json&scope=2";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR place - event search.
	 */
	@Test()
	public void testPlaceEventSearchJsonHeaders() {
		String url = this.domain
				+ "/place/v2/eventsearch?query=美食&event=groupon&region=131&bounds=39.915,116.404,39.935,116.435&output=json&page_size=1&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8", response
				.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * Check for headers. FOR place - event search xml format.
	 */
	@Test()
	public void testPlaceEventSearchXMLHeaders() {
		String url = this.domain
				+ "/place/v2/eventsearch?query=美食&event=groupon&region=131&bounds=39.915,116.404,39.935,116.435&output=xml&page_size=1&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * test place event search for status line..
	 */
	@Test
	public void testPlaceEventSearchXMLStatusLine() {
		String url = this.domain
				+ "/place/v2/eventsearch?query=美食&event=groupon&region=131&bounds=39.915,116.404,39.935,116.435&output=xml&page_size=1&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * test place event search for status line..
	 */
	@Test
	public void testPlaceEventSearchJsonStatusLine() {
		String url = this.domain
				+ "/place/v2/eventsearch?query=美食&event=groupon&region=131&bounds=39.915,116.404,39.935,116.435&output=json&page_size=1&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}


	/**
	 * Check for headers. FOR place - event detail json format.
	 */
	@Test
	public void testPlaceEventDetailJsonHeaders() {
		String url = this.domain
				+ "/place/v2/eventdetail?uid=c14fc238f7fadd4ea5da390f&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * test place event detail for status line..
	 */
	@Test
	public void testPlaceEventDetailJsonStatusLine() {
		String url = this.domain
				+ "/place/v2/eventdetail?uid=c14fc238f7fadd4ea5da390f&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR place - event detail json format.
	 */
	@Test
	public void testPlaceEventDetailXMLHeaders() {
		String url = this.domain
				+ "/place/v2/eventdetail?uid=c14fc238f7fadd4ea5da390f&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}

	/**
	 * test place event detail for status line..
	 */
	@Test
	public void testPlaceEventDetailXMLStatusLine() {
		String url = this.domain
				+ "/place/v2/eventdetail?uid=c14fc238f7fadd4ea5da390f&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * test place event detail for status line..
	 */
	@Test
	public void testPlaceEventDetailJSONContent() {
		String url = this.domain
				+ "/place/v2/eventdetail?uid=c14fc238f7fadd4ea5da390f&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		String expectedResult = "<EventDetailResponse> \r\n  <status>0</status>\r\n  <message>ok</message>\r\n\t\t<result>\r\n\t\t\t\t\t<uid>c14fc238f7fadd4ea5da390f</uid>\r\n\t\t\t\t\t\t\t<name>觉品壹号</name>\r\n\t\t\t\t\t\t\t<location>\r\n\t\t\t\t<lat>39.905738</lat>\r\n\t\t\t\t<lng>116.390222</lng>\r\n\t\t\t</location>\r\n\t\t\t\t\t\t\t<address>西城区南新华街甲1号瑞驰大酒店1楼，和平门全聚德西对面（地铁和平门站D口出）</address>\r\n\t\t\t\t\t\r\n\t\t\t\t\t\r\n\t</result>\r\n</EventDetailResponse>\r\n";
		this.verifyResponse(url, expectedResult);
	}
	
	/**
	 * test place suggestion for status line..
	 */
	@Test
	public void testPlaceSuggestionJsonStatusLine() {
		String url = this.domain
				+ "/place/v2/suggestion?query=天安门&region=131&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR place - suggestion json format.
	 */
	@Test
	public void testPlaceSuggestionJsonHeaders() {
		String url = this.domain
				+ "/place/v2/suggestion?query=天安门&region=131&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test place suggestion for status line..
	 */
	@Test
	public void testPlaceSuggestionXMLStatusLine() {
		String url = this.domain
				+ "/place/v2/suggestion?query=天安门&region=131&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR place - suggestion json format.
	 */
	@Test
	public void testPlaceSuggestionXMLHeaders() {
		String url = this.domain
				+ "/place/v2/suggestion?query=天安门&region=131&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test geocoder for status line..
	 */
	@Test
	public void testGEOCoderJsonStatusLine() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderOption&output=json&address=百度大厦&city=北京市";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR geocoder json call back format.
	 */
	@Test
	public void testGEOCoderJsonCallbackHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderOption&output=json&address=百度大厦&city=北京市";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * Check for headers. FOR geocoder json format.
	 */
	@Test
	public void testGEOCoderJsonHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&output=json&address=百度大厦&city=北京市";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test geocoder for status line..
	 */
	@Test
	public void testGeoCoderXMLStatusLine() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderOption&output=xml&address=百度大厦&city=北京市";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR place - geocoder format.
	 */
	@Test
	public void testGEOCoderXMLHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderOption&output=xml&address=百度大厦&city=北京市";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test RGC for status line..
	 */
	@Test
	public void testRGCJsonStatusLine() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR RGC json call back format.
	 */
	@Test
	public void testRGCJsonCallbackHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * Check for headers. FOR RGC json format.
	 */
	@Test
	public void testRGCJsonHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&location=39.983424,116.322987&output=json&pois=1";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test RGC for status line..
	 */
	@Test
	public void testRGCXMLStatusLine() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&location=39.983424,116.322987&output=xml&pois=1";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR RGC XML format.
	 */
	@Test
	public void testRGCXMLHeaders() {
		String url = this.domain
				+ "/geocoder/v2/?ak=0h8SLfsqRWcZWXiM1iUu2iZk&location=39.983424,116.322987&output=xml&pois=1";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode driving, type2 for status line..
	 */
	@Test
	public void testDirectionDrivingType2JsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=上地五街&destination=北京大学&origin_region=北京&destination_region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode driving, type2 json format.
	 */
	@Test
	public void testDirectionDrivingType2JsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=上地五街&destination=北京大学&origin_region=北京&destination_region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode driving, type2 for status line..
	 */
	@Test
	public void testDirectionDrivingType2XMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=上地五街&destination=北京大学&origin_region=北京&destination_region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode driving, type2 format.
	 */
	@Test
	public void testDirectionDrivingType2XMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=上地五街&destination=北京大学&origin_region=北京&destination_region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode driving, type1 for status line..
	 */
	@Test
	public void testDirectionDrivingType1JsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=人大&destination=北京大学&origin_region=北京&destination_region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode driving, type1 json format.
	 */
	@Test
	public void testDirectionDrivingType1JsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=人大&destination=北京大学&origin_region=北京&destination_region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode driving, type1 for status line..
	 */
	@Test
	public void testDirectionDrivingType1XMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=人大&destination=北京大学&origin_region=北京&destination_region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode driving, type1 format.
	 */
	@Test
	public void testDirectionDrivingType1XMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=driving&origin=人大&destination=北京大学&origin_region=北京&destination_region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode Transit Type2 for status line..
	 */
	@Test
	public void testDirectionTransitType2JsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=上地五街&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode Transit Type2 json format.
	 */
	@Test
	public void testDirectionTransitType2JsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=上地五街&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode Transit Type2 for status line..
	 */
	@Test
	public void testDirectionTransitType2XMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=上地五街&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode Transit Type2 format.
	 */
	@Test
	public void testDirectionTransitType2XMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=上地五街&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode Transit for status line..
	 */
	@Test
	public void testDirectionTransitJsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=人大&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode Transit json format.
	 */
	@Test
	public void testDirectionTransitJsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=人大&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode Transit for status line..
	 */
	@Test
	public void testDirectionTransitXMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=人大&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode Transit  format.
	 */
	@Test
	public void testDirectionTransitXMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=transit&origin=人大&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode walking type2 for status line..
	 */
	@Test
	public void testDirectionWalkingType2JsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=上地五街&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode walking type2 json format.
	 */
	@Test
	public void testDirectionWalkingType2JsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=上地五街&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode walking type2 for status line..
	 */
	@Test
	public void testDirectionWalkingType2XMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=上地五街&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode walking type2  format.
	 */
	@Test
	public void testDirectionWalkingType2XMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=上地五街&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode walking type1 for status line..
	 */
	@Test
	public void testDirectionWalkingType1JsonStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=人大&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode walking type1 json format.
	 */
	@Test
	public void testDirectionWalkingType1JsonHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=人大&destination=北京大学&region=北京&output=json&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode walking type1 for status line..
	 */
	@Test
	public void testDirectionWalkingType1XMLStatusLine() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=人大&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode walking type1 format.
	 */
	@Test
	public void testDirectionWalkingType1XMLHeaders() {
		String url = this.domain
				+ "/direction/v1?mode=walking&origin=人大&destination=北京大学&region=北京&output=xml&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode Routematrix for status line..
	 */
	@Test
	public void testRoutematrixJsonStatusLine() {
		String url = this.domain
				+ "/direction/v1/routematrix?output=json&origins=天安门%7C鸟巢&destinations=北京大学%7C东方明珠&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode Routematrix json format.
	 */
	@Test
	public void testRoutematrixJsonHeaders() {
		String url = this.domain
				+ "/direction/v1/routematrix?output=json&origins=天安门%7C鸟巢&destinations=北京大学%7C东方明珠&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript; charset=UTF-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test mode Routematrix for status line..
	 */
	@Test
	public void testRoutematrixXMLStatusLine() {
		String url = this.domain
				+ "/direction/v1/routematrix?output=xml&origins=天安门%7C鸟巢&destinations=北京大学%7C东方明珠&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}

	/**
	 * Check for headers. FOR mode Routematrix format.
	 */
	@Test
	public void testRoutematrixXMLHeaders() {
		String url = this.domain
				+ "/direction/v1/routematrix?output=xml&origins=天安门%7C鸟巢&destinations=北京大学%7C东方明珠&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/xml;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode IP for status line..
	 */
	@Test
	public void testIPJsonStatusLine() {
		String url = this.domain
				+ "/location/ip?ak=0h8SLfsqRWcZWXiM1iUu2iZk&ip=202.198.16.3&coor=bd09ll";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode IP json format.
	 */
	@Test
	public void testIPJsonHeaders() {
		String url = this.domain
				+ "/location/ip?ak=0h8SLfsqRWcZWXiM1iUu2iZk&ip=202.198.16.3&coor=bd09ll";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("application/json;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
	/**
	 * test direction, mode Geoconv for status line..
	 */
	@Test
	public void testGeoconvJsonStatusLine() {
		String url = this.domain
				+ "/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924&from=1&to=5&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		HttpResponse response = this.getHttpResponse(RequestType.GET, url);
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
				.toString());
	}
	
	/**
	 * Check for headers. FOR mode Geoconv json format.
	 */
	@Test
	public void testGeoconvJsonHeaders() {
		String url = this.domain
				+ "/geoconv/v1/?coords=114.21892734521,29.575429778924;114.21892734521,29.575429778924&from=1&to=5&ak=0h8SLfsqRWcZWXiM1iUu2iZk";
		response = this.getHttpResponse(RequestType.GET, url);

		System.out.println(response);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		Assert.assertEquals("text/javascript;charset=utf-8",
				response.getFirstHeader("Content-Type").getValue());
		Assert.assertEquals("0", response.getFirstHeader("Error-Code")
				.getValue());
		Assert.assertEquals("Apache", response.getFirstHeader("Server")
				.getValue());
		Assert.assertEquals("apimap",
				response.getFirstHeader("Http_x_bd_subsys").getValue());
		Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product")
				.getValue());
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
		Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
	}
	
}
