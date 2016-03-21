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

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.eclipsesource.restfuse.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapi.apitest.datadriven.comparison.ComparisonEngine;
import com.sapi.apitest.datadriven.context.ApplicationContext;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase.RequestType;

/**
 * Base class design for Excel Driven Framework to handle http request. TODO:
 * add RESTFUSE and Transaction roll back support.
 * 
 * @author wanhao01 
 *
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({
// // "classpath:conf-spring/spring-service.xml",
// // "classpath:conf-spring/spring-controller.xml"
// })
// @TransactionConfiguration(transactionManager="transactionManager")
public class TestBase {
    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    protected CloseableHttpClient httpclient = null;

    protected final String responseParsingException = "exception when parsing the response, details: ";

    protected Throwable throwable = null;

    protected HttpResponse response = null;

    protected String domain = getDomain();

    /**
     * Get the domain, the domain is read from the runner argument -urlA.
     * If -urlA is missing or the tests are not running from testrunner(i.e. from junit stub), then read form the config file /config/exceldriven.properties.
     * In most cases, using test runner to trigger the test is suggested.
     * 
     * @return
     */
    private String getDomain() {
        if (ApplicationContext.domainA != null) {
            if (ApplicationContext.domainA.toUpperCase().startsWith("HTTP")) {
                return ApplicationContext.domainA;
            } else {
                return String.format("http://%s", ApplicationContext.domainA);
            }
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(System.getProperty("user.dir")
                    + "/config/exceldriven.properties"));
        } catch (IOException e) {
            logger.error(e);
        }
        return String.format("http://%s",
                properties.getProperty("testcase.domainA"));
    }

    /**
     * Base function for parse request and return HttpResponse, in the test
     * verification, we only need to design check point for the response.
     * 
     * @param type
     * @param uri
     * @return
     */
    protected HttpResponse getHttpResponse(RequestType type, String uri) {
        switch (type) {
            case GET:
                return retrieveGetReponse(uri);
            case POST:
                return retrievePostResponse(uri);
            case DELETE:
                return null;
            case PUT:
                return null;
            default:
                return null;
        }
    }

    /**
     * Handle HTTP POST request for the specific request.
     * 
     * @param uri
     * @return
     */
    private HttpResponse retrievePostResponse(String uri) {
        httpclient = HttpClients.createDefault();
        int split = uri.indexOf("?");
        String apiUri = uri.substring(0, split);
        String[] pairs;
        if (uri.indexOf("&") > 0) {
            pairs = uri.substring(split).split("&");
        } else {
            pairs = uri.substring(split).toUpperCase().split("%3D");
        }
        HttpPost httpPost = new HttpPost(apiUri);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String pair : pairs) {
            nvps.add(new BasicNameValuePair(pair.split("=")[0],
                    pair.split("=")[1]));
        }
        CloseableHttpResponse response = null;
        Throwable throwable = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            throwable = e;
        } catch (ClientProtocolException e) {
            throwable = e;
        } catch (IOException e) {
            throwable = e;
        } finally {
            if (null != throwable) {
                logger.error(throwable);
            }
            try {
                httpclient.close();
            } catch (IOException ignore) {
                logger.error(ignore);
            }
            response = null;
            throwable = null;
        }

        return response;
    }

    /**
     * Parsing the HTTP response, and parsing the json/xml key-value pairs.
     * 
     * @param type
     * @param uri
     * @return
     * 
     * @IMPORTANT:The parsing logic is done through the jackson framework, which
     *                handles the json/xml to map,will will involve drawback of
     *                ignoring the blank values (blank, tab, return)values that
     *                may exist in the json key-value pair.
     */
    protected Map<String, String> getResponsePairs(RequestType type, String uri) {
        HttpResponse response = getHttpResponse(type, uri);
        HttpEntity entity = response.getEntity();

        try {
            String content = EntityUtils.toString(entity, "UTF8");
            ObjectMapper mapper = new ObjectMapper();
            Object contentObject = mapper.readValue(content, Object.class);
            Map<String, String> contentMap = ComparisonEngine
                    .parseParaWithDetailFromJosn(contentObject, "");
            return contentMap;
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                this.httpclient.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return null;
    }

    /***
     * Parsing the response the conver the json/xml formatted body to map.
     * 
     * @param resp
     * @return
     */
    protected Map<String, String> responseToMap(Response resp) {
        ObjectMapper mapper = new ObjectMapper();
        Object contentObject = null;
        Map<String, String> contentMap = null;
        try {
            contentObject = mapper.readValue(resp.getBody(), Object.class);
            contentMap = ComparisonEngine.parseParaWithDetailFromJosn(
                    contentObject, "");
        } catch (IOException e) {
            logger.error(e);
        }
        return contentMap;
    }

    /**
     * Handle HTTP GET request for the specific request.
     * 
     * @param uri
     * @return
     */
    private HttpResponse retrieveGetReponse(String uri) {
        httpclient = HttpClients.createDefault();
        HttpResponse response = null;
        Throwable throwable = null;
        try {
            HttpGet httpget = new HttpGet(uri);

            // System.out.println("Executing request " +
            // httpget.getRequestLine());

            response = httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            throwable = e;
        } catch (IOException e) {
            throwable = e;
        } finally {
            if (null != throwable) {
                logger.error(throwable);
            }
            throwable = null;
        }
        return response;
    }

    /**
     * Verify the content parsing from the GET:url, is equal to the expected
     * Result. with message parameter.
     * 
     * @param url
     * @param expectedResult
     */
    protected void verifyResponse(String url, String expectedResult,
            String message) {
        response = this.getHttpResponse(RequestType.GET, url);
        HttpEntity entity = response.getEntity();
        try {
            String content = EntityUtils.toString(entity, "UTF8");
            Assert.assertEquals(message, expectedResult, content);
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
            } finally {
                throwable = null;
            }
        }
    }

    /**
     * Verify the content parsing from the GET:url, is equal to the expected
     * Result.
     * 
     * @param url
     * @param expectedResult
     */
    protected void verifyResponse(String url, String expectedResult) {
        this.verifyResponse(url, expectedResult, null);
    }
}