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
package com.sapi.apitest.datadriven.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapi.apitest.datadriven.comparison.ComparisonEngine;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase.RequestType;
import com.sapi.apitest.datadriven.exceptions.URLInvalidException;

/**
 * The class encapsulation for handling http related operations.
 * 
 * @author wanhao01
 *
 */
public class HttpRequest {

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    public static final String ERROR_PREFIX = "there's error when invoking the";

    public enum RequestType {
        GET, POST, DELETE, PUT;

        public String toString() {
            String stringValue = name().toUpperCase();
            return stringValue;
        }
    }

    private static Map<String, Integer> map = new HashMap<String, Integer>();

    static {
        map.put("GET", 1);
        map.put("POST", 2);
        map.put("PUT", 3);
        map.put("DELETE", 4);
    }
    
    public static CloseableHttpClient httpclient = null;

    /**
     * Base function for parse request and return HttpResponse, in the test
     * verification, we only need to design check point for the response.
     * 
     * @param type
     * @param uri
     * @return
     */
    public static HttpResponse getHttpResponse(RequestType type, String uri) {
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
    private static HttpResponse retrievePostResponse(String uri) {
        httpclient = HttpClients.createDefault();
        int split = uri.indexOf("?");
        String apiUri = uri.substring(0, split);
        String[] pairs;
        if (uri.indexOf("&") > 0) {
            pairs = uri.substring(split + 1).split("&");
        } else {
            pairs = uri.substring(split + 1).toUpperCase().split("%3D");
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
            throwable = null;
        }

        return response;
    }

    /**
     * Handle HTTP GET request for the specific request.
     * 
     * @param uri
     * @return
     */
    public static HttpResponse retrieveGetReponse(String uri) {
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
     * Send request according to the request type, currently we only support GET
     * and POST request, and by default using the GET request.
     * 
     * @param requestType
     * @param url
     * @return
     */
    public static String sendRequest(String requestType, String url) {

        int reqst = 1;
        if (map.containsKey(requestType)) {
            reqst = map.get(requestType.toUpperCase());
        }
        switch (reqst) {
            case 1:
                return sendGet(url);
            case 2:
                try {
                    HttpResponse response = retrievePostResponse(url);
                    return EntityUtils.toString(response.getEntity());
//                    return sendPost(url);
                } catch (ParseException | IOException e) {
                    logger.error(e);
                }
                return "";
            default:
                return sendGet(url);
        }
    }

    /**
     * Sending Get request.
     * 
     * @param url
     *            the request url.
     * @param param
     *            the parameter format as name1=value1&name2=value2。
     * @return response body.
     */
    public static String sendGet(String domain, String param) {
        String urlNameString = domain + param;
        return sendGet(urlNameString);
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // // 获取所有响应头字段
            // Map<String, List<String>> map = connection.getHeaderFields();
            // // 遍历所有的响应头字段
            // for (String key : map.keySet()) {
            // System.out.println(key + "--->" + map.get(key));
            // }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("{} GET request with the url: {}", ERROR_PREFIX, url);
            logger.error(e);
            result = String.format("{} GET request with the url: %s \n",
                    ERROR_PREFIX, url) + e;
            // e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error(e2);
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Parsing the real url like http://test.domain/context?para1=a&para2=b we
     * will get the api context as http://test.domain/context and the parameter
     * para1=a&para2=b
     * 
     * @param realUrl
     * @return
     * @throws URLInvalidException
     */
    public static String sendPost(String realUrl) throws URLInvalidException {
        int index = realUrl.indexOf("?");
        if (index <= 0) {
            String error = String
                    .format("the url:\" %s \"provided is not valid for the POST request.",
                            realUrl);
            logger.error(error);
            throw new URLInvalidException(error);
        }
        String context = realUrl.substring(0, index);
        String param = realUrl.substring(index + 1);
        return doPost(context, param);
    }

    /**
     * The url of the POST request.
     * 
     * @param url
     *            the url of the request.
     * @param param
     *            post body as name1=value1&name2=value2。
     * @return the response body.
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("{} POST request  with the url {}", ERROR_PREFIX, url);
            logger.error(e);
            // System.out.println("发送 POST 请求出现异常！" + e);
            // e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.error(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        sendRequest("hah", "haha");
        String realUrl = "http://test.domain/context?para1=a&para2=b";
        System.out
                .println(String
                        .format("the url:\" %s \"provided is not valid %s for the POST request.",
                                realUrl, "haha"));
    }
}