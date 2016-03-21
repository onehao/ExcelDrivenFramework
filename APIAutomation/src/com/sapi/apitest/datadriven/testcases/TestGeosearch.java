package com.sapi.apitest.datadriven.testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
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

public class TestGeosearch extends TestBase {

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
	 * Check for headers. FOR Geosearch bound
	 */
	@Test
	public void testBoundHeaders() {
		response = this
				.getHttpResponse(
						RequestType.GET,
						this.domain + "/geosearch/v3/bound?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&sortby=distance%3a1&bounds=116.350658,39.901873;116.446238,39.961848&");
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

	/**
	 * Check for headers. FOR Geosearch nearby
	 */
	@Test
	public void testNearbyHeaders() {
		response = this
				.getHttpResponse(
						RequestType.GET,
						this.domain + "/geosearch/v3/nearby?geotable_id=106989&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=200000&filter%3dmetrotype%3a1%2c1&page_size=50&page_index=0&sortby=distance:1");
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

	/**
	 * Check for headers. FOR Geosearch local
	 */
	@Test
	public void testLocalHeaders() {
		response = this
				.getHttpResponse(
						RequestType.GET,
						this.domain + "/geosearch/v3/local?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&sortby=type2:-1");
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

	/**
	 * Do a complete string compare, as for 1. check for encoding. 2. check for
	 * key-value pair format. and the test is designed to return a single value
	 * array, so as to not affect by the sequence.
	 * 
	 * FOR Geosearch nearby
	 */
	@Test
	public void testContentFormatNearby() {
		String url = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0";
		String expectedResult = "{\"status\":0, \"total\":1, \"size\":1, \"contents\":[{\"category\":\"\\u516C\\u4EA4\", \"category_en\":\"bus\", \"uid\":934137560, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"type2\":2, \"geotable_id\":106990, \"district\":\"\\u4E1C\\u57CE\\u533A\", \"create_time\":1434080106, \"metrotype\":1, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"location\":[116.40434999999999, 39.939799999999998], \"url\":\"http:\\/\\/bus.bj.bendibao.com\\/zhandian\\/station289003.htm\", \"title\":\"\\u5730\\u5B89\\u95E8\\u4E1C\", \"coord_type\":3, \"type\":0, \"distance\":133, \"weight\":0}]}";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * Do a complete string compare, as for 1. check for encoding. 2. check for
	 * key-value pair format. and the test is designed to return a single value
	 * array, so as to not affect by the sequence.
	 * 
	 * FOR Geosearch bounds
	 */
	@Test
	public void testContentFormatBounds() {
		String url = this.domain + "/geosearch/v3/bound?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype%3a2%2c2%7ctype2%3a1%2c1&bounds=116.362084%2c39.905554;116.368409%2c39.916401";
		String expectedResult = "{\"status\":0, \"total\":1, \"size\":1, \"contents\":[{\"category\":\"\\u5730\\u94C1\", \"category_en\":\"metro\", \"uid\":934129208, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"type2\":1, \"geotable_id\":106990, \"district\":\"\\u897F\\u57CE\\u533A\", \"create_time\":1434080007, \"metrotype\":2, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"location\":[116.36376199999999, 39.913024999999998], \"url\":\"http:\\/\\/bj.bendibao.com\\/ditie\\/zdfuxinmen.shtml\", \"title\":\"\\u590D\\u5174\\u95E8\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}]}";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * Do a complete string compare, as for 1. check for encoding. 2. check for
	 * key-value pair format. and the test is designed to return a single value
	 * array, so as to not affect by the sequence.
	 * 
	 * FOR Geosearch local
	 */
	@Test
	public void testContentFormatLocal() {
		String url = this.domain + "/geosearch/v3/local?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan";
		String expectedResult = "{\"status\":0, \"total\":2, \"size\":2, \"contents\":[{\"desc\":\"\\u4EBA\\u591A\\u4F1A\\u6BD4\\u8F83\\u597D\\u73A9\\uFF0C\\u6F02\\u4EAE\\u7684\\u5730\\u65B9\\uFF0C\\u8D5E\\u4E00\\u4E2A\\u98CE\\u666F\\u4E0D\\u9519\\u3002\\u534E\\u5317\\u5730\\u533A\\u552F\\u4E00\\u4EE5\\u5CA9\\u6EB6\\u5CF0\\u6797\\u3002\", \"star\":4, \"tags\":\"yazhou zhongguo beijing fangshan 3014 3033 4020 4025 2001 3020 3016 4005 4003 4004 4006 4007 4008 4009 4010 4013 4014 4015 4019 4021 4022 4023 1021 7001 7003 7006 7005 503\", \"uid\":949343848, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"going_count\":1301, \"geotable_id\":106994, \"district\":\"\\u623F\\u5C71\\u533A\", \"icon_style_id\":\"sid1\", \"score\":0, \"create_time\":1434455868, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"scene_layer\":5, \"location\":[115.60021302600001, 39.641817094799997], \"view_count\":948841, \"gone_count\":933, \"title\":\"\\u5341\\u6E21\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}, {\"desc\":\"\\u4EBA\\u591A\\u4F1A\\u6BD4\\u8F83\\u597D\\u73A9\\uFF0C\\u6F02\\u4EAE\\u7684\\u5730\\u65B9\\uFF0C\\u8D5E\\u4E00\\u4E2A\\u98CE\\u666F\\u4E0D\\u9519\\u3002\\u534E\\u5317\\u5730\\u533A\\u552F\\u4E00\\u4EE5\\u5CA9\\u6EB6\\u5CF0\\u6797\\u3002\", \"star\":4, \"tags\":\"yazhou zhongguo beijing fangshan 3014 3033 4020 4025 2001 3020 3016 4005 4003 4004 4006 4007 4008 4009 4010 4013 4014 4015 4019 4021 4022 4023 1021 7001 7003 7006 7005 503\", \"uid\":949355838, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"going_count\":1301, \"geotable_id\":106994, \"district\":\"\\u623F\\u5C71\\u533A\", \"icon_style_id\":\"sid1\", \"score\":0, \"create_time\":1434456042, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"scene_layer\":5, \"location\":[115.60021302600001, 39.641817094799997], \"view_count\":948841, \"gone_count\":933, \"title\":\"\\u5341\\u6E21\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}]}";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * The tests will cover: 1. The special case not cover by excel. 2. The
	 * tests couldn't be done or supported by the excel driven framework. 3. The
	 * expected result maybe constant.
	 * 
	 * 
	 * IMPORTANT NOTE:
	 * 
	 */
	@Test
	public void testResponseDemo() {
		Map<String, String> map = this
				.getResponsePairs(
						RequestType.GET,
						this.domain + "/geosearch/v3/local?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan");
		Assert.assertEquals("0", map.get("status"));
		Assert.assertEquals("2", map.get("total"));
		Assert.assertEquals("2", map.get("size"));
		Assert.assertEquals("2", map.get("contents#"));
	}

	/**
	 * Check call back functionality.
	 * 
	 * FOR Geosearch local
	 */
	@Test
	public void testCallBackLocal() {
		String url = this.domain + "/geosearch/v3/local?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan&callback=jsonback";
		String expectedResult = "jsonback && jsonback({\"status\":0, \"total\":2, \"size\":2, \"contents\":[{\"desc\":\"\\u4EBA\\u591A\\u4F1A\\u6BD4\\u8F83\\u597D\\u73A9\\uFF0C\\u6F02\\u4EAE\\u7684\\u5730\\u65B9\\uFF0C\\u8D5E\\u4E00\\u4E2A\\u98CE\\u666F\\u4E0D\\u9519\\u3002\\u534E\\u5317\\u5730\\u533A\\u552F\\u4E00\\u4EE5\\u5CA9\\u6EB6\\u5CF0\\u6797\\u3002\", \"star\":4, \"tags\":\"yazhou zhongguo beijing fangshan 3014 3033 4020 4025 2001 3020 3016 4005 4003 4004 4006 4007 4008 4009 4010 4013 4014 4015 4019 4021 4022 4023 1021 7001 7003 7006 7005 503\", \"uid\":949343848, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"going_count\":1301, \"geotable_id\":106994, \"district\":\"\\u623F\\u5C71\\u533A\", \"icon_style_id\":\"sid1\", \"score\":0, \"create_time\":1434455868, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"scene_layer\":5, \"location\":[115.60021302600001, 39.641817094799997], \"view_count\":948841, \"gone_count\":933, \"title\":\"\\u5341\\u6E21\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}, {\"desc\":\"\\u4EBA\\u591A\\u4F1A\\u6BD4\\u8F83\\u597D\\u73A9\\uFF0C\\u6F02\\u4EAE\\u7684\\u5730\\u65B9\\uFF0C\\u8D5E\\u4E00\\u4E2A\\u98CE\\u666F\\u4E0D\\u9519\\u3002\\u534E\\u5317\\u5730\\u533A\\u552F\\u4E00\\u4EE5\\u5CA9\\u6EB6\\u5CF0\\u6797\\u3002\", \"star\":4, \"tags\":\"yazhou zhongguo beijing fangshan 3014 3033 4020 4025 2001 3020 3016 4005 4003 4004 4006 4007 4008 4009 4010 4013 4014 4015 4019 4021 4022 4023 1021 7001 7003 7006 7005 503\", \"uid\":949355838, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"going_count\":1301, \"geotable_id\":106994, \"district\":\"\\u623F\\u5C71\\u533A\", \"icon_style_id\":\"sid1\", \"score\":0, \"create_time\":1434456042, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"scene_layer\":5, \"location\":[115.60021302600001, 39.641817094799997], \"view_count\":948841, \"gone_count\":933, \"title\":\"\\u5341\\u6E21\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}]})";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * Check call back functionality.
	 * 
	 * FOR Geosearch nearby
	 */
	@Test
	public void testCallBackNearby() {
		String url = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0&callback=jsonbacknearby";
		String expectedResult = "jsonbacknearby && jsonbacknearby({\"status\":0, \"total\":1, \"size\":1, \"contents\":[{\"category\":\"\\u516C\\u4EA4\", \"category_en\":\"bus\", \"uid\":934137560, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"type2\":2, \"geotable_id\":106990, \"district\":\"\\u4E1C\\u57CE\\u533A\", \"create_time\":1434080106, \"metrotype\":1, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"location\":[116.40434999999999, 39.939799999999998], \"url\":\"http:\\/\\/bus.bj.bendibao.com\\/zhandian\\/station289003.htm\", \"title\":\"\\u5730\\u5B89\\u95E8\\u4E1C\", \"coord_type\":3, \"type\":0, \"distance\":133, \"weight\":0}]})";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * Check call back functionality.
	 * 
	 * FOR Geosearch bounds
	 */
	@Test
	public void testCallBackBounds() {
		String url = this.domain + "/geosearch/v3/bound?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype%3a2%2c2%7ctype2%3a1%2c1&bounds=116.362084%2c39.905554;116.368409%2c39.916401&callback=jsonbackbounds";
		String expectedResult = "jsonbackbounds && jsonbackbounds({\"status\":0, \"total\":1, \"size\":1, \"contents\":[{\"category\":\"\\u5730\\u94C1\", \"category_en\":\"metro\", \"uid\":934129208, \"province\":\"\\u5317\\u4EAC\\u5E02\", \"type2\":1, \"geotable_id\":106990, \"district\":\"\\u897F\\u57CE\\u533A\", \"create_time\":1434080007, \"metrotype\":2, \"city\":\"\\u5317\\u4EAC\\u5E02\", \"location\":[116.36376199999999, 39.913024999999998], \"url\":\"http:\\/\\/bj.bendibao.com\\/ditie\\/zdfuxinmen.shtml\", \"title\":\"\\u590D\\u5174\\u95E8\", \"coord_type\":3, \"type\":0, \"distance\":0, \"weight\":0}]})";
		this.verifyResponse(url, expectedResult);
	}

	/**
	 * Check call back functionality for exception cases.
	 * 
	 * FOR Geosearch bounds call for error cases.
	 * 
	 */
	@Test
	public void testCallBackBoundsErrors() {
		HashMap<String, String> reqPairs = new HashMap<String, String>();
		// no permission to access this geotable.
		String case1 = this.domain + "/geosearch/v3/bound?geotable_id=1069901&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype%3a2%2c2%7ctype2%3a1%2c1&bounds=116.362084%2c39.905554;116.368409%2c39.916401&callback=jsonbackbounds";
		String case1ExpRst = "jsonbackbounds && jsonbackbounds({\"status\":1233, \"message\":\"Access denied, you have no permission to access this geotable\"})";
		
		//ak invalid.
		String case2 = this.domain + "/geosearch/v3/bound?geotable_id=106990&ak=1Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype%3a2%2c2%7ctype2%3a1%2c1&bounds=116.362084%2c39.905554;116.368409%2c39.916401&callback=jsonbackbounds";
		String case2ExpRst = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
		
		//filter invalid.
		String case3 = this.domain + "/geosearch/v3/bound?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype1%3a2%2c2%7ctype2%3a1%2c1&bounds=116.362084%2c39.905554;116.368409%2c39.916401&callback=jsonbackbounds";
		String case3ExpRst = "jsonbackbounds && jsonbackbounds({\"status\":2, \"message\":\"Parameter invalid, the key input with filter parameter not exist\"})";
		
		//bounds parameter missing.
		String case4 = this.domain + "/geosearch/v3/bound?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=50&page_index=0&filter=metrotype%3a2%2c2%7ctype2%3a1%2c1&bound=116.362084%2c39.905554;116.368409%2c39.916401&callback=jsonbackbounds";
		String case4ExpRst = "jsonbackbounds && jsonbackbounds({\"status\":2, \"message\":\"Parameter invalid, please check bounds parameter\"})";
		
		reqPairs.put(case1, case1ExpRst);
		reqPairs.put(case2, case2ExpRst);
		reqPairs.put(case3, case3ExpRst);
		reqPairs.put(case4, case4ExpRst);

		for (Entry<String, String> reqPair : reqPairs.entrySet()) {
			verifyResponse(reqPair);
		}
	}
	
	/**
	 * Check call back functionality for exception cases.
	 * 
	 * FOR Geosearch nearby call for error cases.
	 * 
	 */
	@Test
	public void testCallBackNearbyErrors() {
		HashMap<String, String> reqPairs = new HashMap<String, String>();
		// no permission to access this geotable.
		String case1 = this.domain + "/geosearch/v3/nearby?geotable_id=1069901&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0&callback=jsonbacknearby";
		String case1ExpRst = "jsonbacknearby && jsonbacknearby({\"status\":1233, \"message\":\"Access denied, you have no permission to access this geotable\"})";
		
		//ak invalid. api-bugs #484
		String case2 = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=1Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0&callback=jsonbacknearby";
		String case2ExpRst = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
		
		//filter invalid.
		String case3 = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=116.404772,39.938644&radius=150&page_size=50&page_index=0&filter=abc:1&callback=jsonbacknearby";
		String case3ExpRst = "jsonbacknearby && jsonbacknearby({\"status\":2, \"message\":\"Parameter invalid, the key input with filter parameter not exist\"})";
		
		//bounds parameter missing.
		String case4 = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&radius=150&page_size=50&page_index=0&callback=jsonbacknearby";
		String case4ExpRst = "jsonbacknearby && jsonbacknearby({\"status\":2, \"message\":\"Parameter invalid, please check location parameter\"})";
		
		//location not valid.
		String case5 = this.domain + "/geosearch/v3/nearby?geotable_id=106990&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&location=1116.404772,39.938644&radius=150&page_size=50&page_index=0&callback=jsonbacknearby";
		String case5ExpRst = "jsonbacknearby && jsonbacknearby({\"status\":2, \"message\":\"Parameter invalid, please check location parameter\"})";
		
		reqPairs.put(case1, case1ExpRst);
		reqPairs.put(case2, case2ExpRst);
		reqPairs.put(case3, case3ExpRst);
		reqPairs.put(case4, case4ExpRst);
		reqPairs.put(case5, case5ExpRst);

		for (Entry<String, String> reqPair : reqPairs.entrySet()) {
			verifyResponse(reqPair);
		}
	}

	private void verifyResponse(Entry<String, String> reqPair) {
		response = this
				.getHttpResponse(
						RequestType.GET,
						reqPair.getKey());
		HttpEntity entity = response.getEntity();
		try {
			String content = EntityUtils.toString(entity, "UTF8");
			Assert.assertEquals(
					reqPair.getValue(),
					content);
		} catch (ParseException e) {
			throwable = e;
		} catch (IOException e) {
			throwable = e;
		} finally {
			if (null != throwable) {
				Assert.fail(this.responseParsingException + throwable);
			}
			try {
				this.httpclient.close();
			} catch (IOException ignore) {
				// ignore.
			}
		}
	}
	
	/**
	 * Check call back functionality for exception cases.
	 * 
	 * FOR Geosearch local call for error cases.
	 * 
	 */
	@Test
	public void testCallBackLocalErrors() {
		HashMap<String, String> reqPairs = new HashMap<String, String>();
		// no permission to access this geotable.
		String case1 = this.domain + "/geosearch/v3/local?geotable_id=1069941&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan&callback=jsonback";
		String case1ExpRst = "jsonback && jsonback({\"status\":1233, \"message\":\"Access denied, you have no permission to access this geotable\"})";
		
		//ak invalid. api-bugs #484
		String case2 = this.domain + "/geosearch/v3/local?geotable_id=1069941&ak=1Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan&callback=jsonback";
		String case2ExpRst = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
		
		//filter invalid.
		String case3 = this.domain + "/geosearch/v3/local?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&page_size=10&page_index=0&tags=beijing%204005%20fangshan&filter=a:1&callback=jsonback";
		String case3ExpRst = "jsonback && jsonback({\"status\":2, \"message\":\"Parameter invalid, the key input with filter parameter not exist\"})";
		
		//ak parameter missing.
		String case4 = this.domain + "/geosearch/v3/local?geotable_id=106994&page_size=10&page_index=0&tags=beijing%204005%20fangshan&callback=jsonback";
		String case4ExpRst = "{\"status\":101,\"message\":\"AK参数不存在\"}";
		
		reqPairs.put(case1, case1ExpRst);
		reqPairs.put(case2, case2ExpRst);
		reqPairs.put(case3, case3ExpRst);
		reqPairs.put(case4, case4ExpRst);

		for (Entry<String, String> reqPair : reqPairs.entrySet()) {
			verifyResponse(reqPair);
		}
	}
	
	/**
	 * Top3 user scenario:停车场管理
	 * http://api.map.baidu.com/geosearch/v3/local?region=%E5%8C%97%E4%BA%AC&ak=6dfdb2d835c6673ae359c796bba28197&geotable_id=63871&page_size=50&page_index=40
	 */
	@Test
	public void testLocalUserScenario(){
		Map<String, String> map = this
				.getResponsePairs(
						RequestType.GET,
						this.domain + "/geosearch/v3/local?region=%E5%8C%97%E4%BA%AC&ak=6dfdb2d835c6673ae359c796bba28197&geotable_id=63871&page_size=50&page_index=40");
		Assert.assertEquals("0", map.get("status"));
		Assert.assertEquals("3079", map.get("total"));
		Assert.assertEquals("50", map.get("size"));
		Assert.assertEquals("50", map.get("contents#"));
	}

	/**
	 * 单一自定义filter验证,整型连续区间,metrotype
	 * 
	 * http://api.map.baidu.com/geosearch/v3/local?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&region=%E5%8C%97%E4%BA%AC%E5%B8%82&page_size=50&filter=star:2,4
	 */
	@Test
	public void testLocal1(){
		Map<String, String> map = this
				.getResponsePairs(
						RequestType.GET,
						this.domain + "/geosearch/v3/local?region=%E5%8C%97%E4%BA%AC&ak=6dfdb2d835c6673ae359c796bba28197&geotable_id=63871&page_size=50&page_index=40");
		Assert.assertEquals("0", map.get("status"));
		Assert.assertEquals("3079", map.get("total"));
		Assert.assertEquals("50", map.get("size"));
		Assert.assertEquals("50", map.get("contents#"));
	}
}
