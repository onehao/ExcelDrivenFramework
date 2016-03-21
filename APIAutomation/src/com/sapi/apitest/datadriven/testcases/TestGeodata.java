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
package com.sapi.apitest.datadriven.testcases;

import java.util.Map;

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

public class TestGeodata extends TestBase {

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
            .getStackTrace()[1].getClassName());
    private static int count = 0;
    
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
        System.out.println(count++);
    }

    //@Test
//    @UseCase(description = "fast fail", isFastFail = true)
//    public void test() {
//        ExcelDrivenUT ut = new ExcelDrivenUT("D:\\workspace\\satp2\\APIAutomation\\config\\geodata.properties");
//        ut.getAndExecuteTestCases();
//    }
    
    /**
     * test response header for detail interface.
     */
    @Test
    public void testHeaderDetail()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=110358");
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertEquals("zh_CN", response.getLocale().toString());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
        
    }
    
    /**
     * test response status for detail interface.
     */
    @Test
    public void testStatusLineDetail()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=110358");
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
    }
    
    /**
     * test response header for list interface.
     */
    @Test
    public void testHeaderList()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/geotable/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF");
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
    }
    
    /**
     * test response status for list interface.
     */
    @Test
    public void testStatusLineList()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/geotable/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF");
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
    }
    
    /**
     * Test list api could retrieve the pois. 
     */
    @Test
    public void testGeotableList(){
        String url = this.domain + "/geodata/v3/geotable/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF";
        String expectedResult = "{\"status\":0,\"geotables\":[{\"name\":\"geo\\u8868_\\u70b911\",\"geotype\":1,\"is_published\":0,\"create_time\":1434640388,\"modify_time\":1434640708,\"id\":110358},{\"name\":\"geo\\u8868_\\u70b910\",\"geotype\":1,\"is_published\":1,\"create_time\":1433489197,\"modify_time\":1433489197,\"id\":106996},{\"name\":\"geo\\u8868_\\u70b99\",\"geotype\":1,\"is_published\":1,\"create_time\":1433489184,\"modify_time\":1433489184,\"id\":106995},{\"create_time\":1433489168,\"geotype\":1,\"is_published\":1,\"modify_time\":1434438799,\"name\":\"geo\\u8868_\\u70b98\\u4e16\\u754c\\u666f\\u70b9\",\"id\":106994},{\"create_time\":1433489151,\"geotype\":1,\"is_published\":1,\"modify_time\":1434077470,\"name\":\"geo\\u8868_\\u70b97\",\"id\":106993},{\"create_time\":1433489115,\"geotype\":1,\"is_published\":1,\"modify_time\":1434348600,\"name\":\"geo\\u8868_\\u70b96\\u666f\\u70b9\",\"id\":106992},{\"create_time\":1433489043,\"geotype\":1,\"is_published\":1,\"modify_time\":1434346333,\"name\":\"geo\\u8868_\\u70b95metrobus\",\"id\":106990},{\"create_time\":1433489000,\"geotype\":1,\"is_published\":1,\"modify_time\":1434014738,\"name\":\"geo\\u8868_\\u70b94\\u5317\\u4eac\\u5468\\u8fb9100km\\u5730\\u94c1\\u7ecf\\u7eac\",\"id\":106989},{\"create_time\":1433488962,\"geotype\":1,\"is_published\":1,\"modify_time\":1433937180,\"name\":\"geo\\u8868_\\u70b93_\\u56fd\\u9645\\u951a\\u70b9\",\"id\":106988},{\"name\":\"geo\\u8868_\\u70b92\",\"geotype\":1,\"is_published\":1,\"create_time\":1433488921,\"modify_time\":1433488921,\"id\":106987},{\"name\":\"geo\\u8868_\\u97621\",\"geotype\":3,\"is_published\":1,\"create_time\":1433487848,\"modify_time\":1433487848,\"id\":106984},{\"name\":\"geo\\u8868_\\u7ebf1\",\"geotype\":2,\"is_published\":1,\"create_time\":1433487754,\"modify_time\":1433487754,\"id\":106983},{\"name\":\"geo\\u8868_\\u70b9\",\"geotype\":1,\"is_published\":1,\"create_time\":1433485682,\"modify_time\":1433485682,\"id\":106970}],\"size\":13,\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test list name parameter. 
     */
    @Test
    public void testGeotableListNameParameter(){
        String url = this.domain + "/geodata/v3/geotable/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&name=geo表_点11";
        String expectedResult = "{\"status\":0,\"geotables\":[{\"name\":\"geo\\u8868_\\u70b911\",\"geotype\":1,\"is_published\":0,\"create_time\":1434640388,\"modify_time\":1434640708,\"id\":110358}],\"size\":1,\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test list ak invalid. 
     */
    @Test
    public void testGeotableListAkInvalid(){
        String url = this.domain + "/geodata/v3/geotable/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF1";
        String expectedResult = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test list sn. 
     */
    @Test
    public void testGeotableListSN(){
        String url = this.domain + "/geodata/v3/geotable/list?ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=ef4a5d60d5235ca672c48709544077ad";
        String expectedResult = "{\"status\":0,\"geotables\":[{\"name\":\"geo\\u8868_\\u70b911\",\"geotype\":1,\"is_published\":0,\"create_time\":1434640388,\"modify_time\":1434640708,\"id\":110358},{\"name\":\"geo\\u8868_\\u70b910\",\"geotype\":1,\"is_published\":1,\"create_time\":1433489197,\"modify_time\":1433489197,\"id\":106996},{\"name\":\"geo\\u8868_\\u70b99\",\"geotype\":1,\"is_published\":1,\"create_time\":1433489184,\"modify_time\":1433489184,\"id\":106995},{\"create_time\":1433489168,\"geotype\":1,\"is_published\":1,\"modify_time\":1434438799,\"name\":\"geo\\u8868_\\u70b98\\u4e16\\u754c\\u666f\\u70b9\",\"id\":106994},{\"create_time\":1433489151,\"geotype\":1,\"is_published\":1,\"modify_time\":1434077470,\"name\":\"geo\\u8868_\\u70b97\",\"id\":106993},{\"create_time\":1433489115,\"geotype\":1,\"is_published\":1,\"modify_time\":1434348600,\"name\":\"geo\\u8868_\\u70b96\\u666f\\u70b9\",\"id\":106992},{\"create_time\":1433489043,\"geotype\":1,\"is_published\":1,\"modify_time\":1434346333,\"name\":\"geo\\u8868_\\u70b95metrobus\",\"id\":106990},{\"create_time\":1433489000,\"geotype\":1,\"is_published\":1,\"modify_time\":1434014738,\"name\":\"geo\\u8868_\\u70b94\\u5317\\u4eac\\u5468\\u8fb9100km\\u5730\\u94c1\\u7ecf\\u7eac\",\"id\":106989},{\"create_time\":1433488962,\"geotype\":1,\"is_published\":1,\"modify_time\":1433937180,\"name\":\"geo\\u8868_\\u70b93_\\u56fd\\u9645\\u951a\\u70b9\",\"id\":106988},{\"name\":\"geo\\u8868_\\u70b92\",\"geotype\":1,\"is_published\":1,\"create_time\":1433488921,\"modify_time\":1433488921,\"id\":106987},{\"name\":\"geo\\u8868_\\u97621\",\"geotype\":3,\"is_published\":1,\"create_time\":1433487848,\"modify_time\":1433487848,\"id\":106984},{\"name\":\"geo\\u8868_\\u7ebf1\",\"geotype\":2,\"is_published\":1,\"create_time\":1433487754,\"modify_time\":1433487754,\"id\":106983},{\"name\":\"geo\\u8868_\\u70b9\",\"geotype\":1,\"is_published\":1,\"create_time\":1433485682,\"modify_time\":1433485682,\"id\":106970}],\"size\":13,\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    
    /**
     * Test detail. 
     */
    @Test
    public void testGeotableDetail(){
        String url = this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=110358";
        String expectedResult = "{\"status\":0,\"geotable\":{\"name\":\"geo\\u8868_\\u70b911\",\"geotype\":1,\"is_published\":0,\"create_time\":1434640388,\"modify_time\":1434640708,\"id\":110358},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test detail, geotable not exist.
     */
    @Test
    public void testGeotableDetailTableNotExist(){
        String url = this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=1103581";
        String expectedResult = "{\"status\":1004,\"message\":\"geotable\\u4e0d\\u5b58\\u5728\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test detail, missing id parameter.
     */
    @Test
    public void testGeotableDetailMissId(){
        String url = this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF";
        String expectedResult = "{\"status\":2,\"message\":\"id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test detail ak invalid. 
     */
    @Test
    public void testGeotableDetailAkInvalid(){
        String url = this.domain + "/geodata/v3/geotable/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF1";
        String expectedResult = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
        verifyResponse(url, expectedResult);
    }
 
    /**
     * Test detail sn. 
     */
    @Test
    public void testGeotableDetailSN(){
        String url = this.domain + "/geodata/v3/geotable/detail?id=110358&ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=b29c99c8d971728752d2410794276da3";
        String expectedResult = "{\"status\":0,\"geotable\":{\"name\":\"geo\\u8868_\\u70b911\",\"geotype\":1,\"is_published\":0,\"create_time\":1434640388,\"modify_time\":1434640708,\"id\":110358},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with required parameter.
     */
    @Test
    public void testColumnList(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970";
        String expectedResult = "{\"status\":0,\"size\":11,\"columns\":[{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760},{\"key\":\"category_int\",\"type\":1,\"geotable_id\":106970,\"name\":\"\\u7c7b\\u578b_int\",\"default_value\":1,\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:13:11\",\"modify_time\":\"2015-06-08 16:13:11\",\"id\":142743},{\"key\":\"testcase\",\"type\":3,\"geotable_id\":106970,\"name\":\"testcase\\u6807\\u8bb0\",\"max_length\":128,\"default_value\":\"Strategy1_category1\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:08:56\",\"modify_time\":\"2015-06-08 16:08:56\",\"id\":142741},{\"key\":\"link2\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u8be6\\u60c5\\u94fe\\u63a52_string\",\"max_length\":256,\"default_value\":\"http:\\/\\/baidu.com\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:07:02\",\"modify_time\":\"2015-06-08 16:07:02\",\"id\":142739},{\"key\":\"link\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u8be6\\u60c5\\u94fe\\u63a5_string\",\"max_length\":256,\"default_value\":\"http:\\/\\/baidu.com\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:05:56\",\"modify_time\":\"2015-06-08 16:05:56\",\"id\":142738},{\"create_time\":\"2015-06-08 16:02:35\",\"default_value\":\"\\u9ed8\\u8ba4 str&#039;&quot;!@#$%^&amp;*()_+{}\\\\|\\\\\\/&lt;&gt;,.\\uffe5#%&amp;*\\uff08\\uff09\\uff01@~{}|\\u2018\\u201c\",\"geotable_id\":106970,\"is_index_field\":0,\"is_search_field\":1,\"is_sortfilter_field\":0,\"is_unique_field\":0,\"key\":\"address2\",\"max_length\":256,\"modify_time\":\"2015-06-08 17:27:42\",\"name\":\"\\u5730\\u5740_string\",\"type\":3,\"id\":142737},{\"key\":\"star\",\"type\":2,\"geotable_id\":106970,\"name\":\"\\u661f\\u8bc4_double\",\"default_value\":123.456789,\"is_sortfilter_field\":1,\"sortfilter_id\":2,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:52:49\",\"modify_time\":\"2015-06-08 15:52:49\",\"id\":142733},{\"key\":\"telphone\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u7535\\u8bdd_string\",\"max_length\":128,\"default_value\":\"(010)12345678, 010-87654321\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:50:19\",\"modify_time\":\"2015-06-08 15:50:19\",\"id\":142732},{\"key\":\"picture\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_string\",\"max_length\":256,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:47:56\",\"modify_time\":\"2015-06-08 15:47:56\",\"id\":142731},{\"key\":\"price\",\"type\":2,\"geotable_id\":106970,\"name\":\"\\u4ef7\\u683c_double\",\"default_value\":1.23,\"is_sortfilter_field\":1,\"sortfilter_id\":1,\"is_search_field\":0,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:44:51\",\"modify_time\":\"2015-06-08 15:44:51\",\"id\":142729},{\"key\":\"category\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u7c7b\\u578b_String\",\"max_length\":45,\"default_value\":\"\\u7c7b\\u578b_\\u9ed8\\u8ba4\\u503c\\u3002\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:39:29\",\"modify_time\":\"2015-06-08 15:39:29\",\"id\":142719}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with name parameter.
     */
    @Test
    public void testColumnListNameParameter(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&name=%E5%9B%BE%E7%89%87";
        String expectedResult = "{\"status\":0,\"size\":2,\"columns\":[{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760},{\"key\":\"picture\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_string\",\"max_length\":256,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:47:56\",\"modify_time\":\"2015-06-08 15:47:56\",\"id\":142731}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with key parameter.
     */
    @Test
    public void testColumnListKeyParameter(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&key=picture2";
        String expectedResult = "{\"status\":0,\"size\":1,\"columns\":[{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with missing geotable_id parameter.
     */
    @Test
    public void testColumnListMissingGeotableid(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF";
        String expectedResult = "{\"status\":2,\"message\":\"geotable_id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with invalid geotable_id parameter.
     */
    @Test
    public void testColumnListInvalidGeotableId(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=11";
        String expectedResult = "{\"status\":1004,\"message\":\"geotable\\u4e0d\\u5b58\\u5728\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with invalid ak parameter.
     */
    @Test
    public void testColumnListInvalidak(){
        String url = this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF1";
        String expectedResult = "{\"status\":200,\"message\":\"APP不存在，AK有误请检查再重试\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * Test column list api with missing ak parameter.
     */
    @Test
    public void testColumnListMissingAK(){
        String url = this.domain + "/geodata/v3/column/list";
        String expectedResult = "{\"status\":101,\"message\":\"AK参数不存在\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test response header for column list interface.
     */
    @Test
    public void testHeaderColumnList()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970");
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
    }
    
    /**
     * test response status for column list interface.
     */
    @Test
    public void testStatusLineColumnList()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/column/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970");
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
    }
    
    /**
     * test sn verification for column list interface.
     */
    @Test
    public void testColumnListSN()
    {
        String url = this.domain + "/geodata/v3/column/list?geotable_id=106970&ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=243628011b13e52c2d92883baae305ed";
        String expectedResult = "{\"status\":0,\"size\":11,\"columns\":[{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760},{\"key\":\"category_int\",\"type\":1,\"geotable_id\":106970,\"name\":\"\\u7c7b\\u578b_int\",\"default_value\":1,\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:13:11\",\"modify_time\":\"2015-06-08 16:13:11\",\"id\":142743},{\"key\":\"testcase\",\"type\":3,\"geotable_id\":106970,\"name\":\"testcase\\u6807\\u8bb0\",\"max_length\":128,\"default_value\":\"Strategy1_category1\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:08:56\",\"modify_time\":\"2015-06-08 16:08:56\",\"id\":142741},{\"key\":\"link2\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u8be6\\u60c5\\u94fe\\u63a52_string\",\"max_length\":256,\"default_value\":\"http:\\/\\/baidu.com\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:07:02\",\"modify_time\":\"2015-06-08 16:07:02\",\"id\":142739},{\"key\":\"link\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u8be6\\u60c5\\u94fe\\u63a5_string\",\"max_length\":256,\"default_value\":\"http:\\/\\/baidu.com\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:05:56\",\"modify_time\":\"2015-06-08 16:05:56\",\"id\":142738},{\"create_time\":\"2015-06-08 16:02:35\",\"default_value\":\"\\u9ed8\\u8ba4 str&#039;&quot;!@#$%^&amp;*()_+{}\\\\|\\\\\\/&lt;&gt;,.\\uffe5#%&amp;*\\uff08\\uff09\\uff01@~{}|\\u2018\\u201c\",\"geotable_id\":106970,\"is_index_field\":0,\"is_search_field\":1,\"is_sortfilter_field\":0,\"is_unique_field\":0,\"key\":\"address2\",\"max_length\":256,\"modify_time\":\"2015-06-08 17:27:42\",\"name\":\"\\u5730\\u5740_string\",\"type\":3,\"id\":142737},{\"key\":\"star\",\"type\":2,\"geotable_id\":106970,\"name\":\"\\u661f\\u8bc4_double\",\"default_value\":123.456789,\"is_sortfilter_field\":1,\"sortfilter_id\":2,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:52:49\",\"modify_time\":\"2015-06-08 15:52:49\",\"id\":142733},{\"key\":\"telphone\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u7535\\u8bdd_string\",\"max_length\":128,\"default_value\":\"(010)12345678, 010-87654321\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:50:19\",\"modify_time\":\"2015-06-08 15:50:19\",\"id\":142732},{\"key\":\"picture\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_string\",\"max_length\":256,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:47:56\",\"modify_time\":\"2015-06-08 15:47:56\",\"id\":142731},{\"key\":\"price\",\"type\":2,\"geotable_id\":106970,\"name\":\"\\u4ef7\\u683c_double\",\"default_value\":1.23,\"is_sortfilter_field\":1,\"sortfilter_id\":1,\"is_search_field\":0,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:44:51\",\"modify_time\":\"2015-06-08 15:44:51\",\"id\":142729},{\"key\":\"category\",\"type\":3,\"geotable_id\":106970,\"name\":\"\\u7c7b\\u578b_String\",\"max_length\":45,\"default_value\":\"\\u7c7b\\u578b_\\u9ed8\\u8ba4\\u503c\\u3002\",\"is_sortfilter_field\":0,\"is_search_field\":1,\"is_index_field\":1,\"is_unique_field\":0,\"create_time\":\"2015-06-08 15:39:29\",\"modify_time\":\"2015-06-08 15:39:29\",\"id\":142719}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     *  test column detail api with required parameters.
     */
    @Test
    public void testColumnDetail(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=142760";
        String expectedResult = "{\"status\":0,\"column\":{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     *  test column detail sn
     */
    @Test
    public void testColumnDetailSn(){
        String url = this.domain + "/geodata/v3/column/detail?geotable_id=106970&id=142760&ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=61010f9f382d220cd91928e65d0b8026";
        String expectedResult = "{\"status\":0,\"column\":{\"key\":\"picture2\",\"type\":4,\"geotable_id\":106970,\"name\":\"\\u56fe\\u7247_type4\",\"max_length\":0,\"default_value\":\"https:\\/\\/www.baidu.com\\/img\\/bdlogo.png\",\"is_sortfilter_field\":0,\"is_search_field\":0,\"is_index_field\":0,\"is_unique_field\":0,\"create_time\":\"2015-06-08 16:25:26\",\"modify_time\":\"2015-06-08 16:25:26\",\"id\":142760},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test response header for column list interface.
     */
    @Test
    public void testHeaderColumnDetail()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=142760");
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
    }
    
    /**
     * test response status for column list interface.
     */
    @Test
    public void testStatusLineColumnDetail()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=142760");
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
    }
    
    /**
     * test column detail api with ak parameter missing.
     */
    @Test
    public void testColumnDetailAkMissing(){
        String url = this.domain + "/geodata/v3/column/detail?geotable_id=106970&id=142760";
        String expectedResult = "{\"status\":101,\"message\":\"AK参数不存在\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test column detail api with ak parameter missing.
     */
    @Test
    public void testColumnDetailIdMissing(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970";
        String expectedResult = "{\"status\":2,\"message\":\"id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test column detail api with ak parameter not exist.
     */
    @Test
    public void testColumnDetailIdNotExist(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=123";
        String expectedResult = "{\"status\":0,\"column\":null,\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test column detail api with ak parameter Invalid.
     */
    @Test
    public void testColumnDetailIdInvalid(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=abc";
        String expectedResult = "{\"status\":2,\"message\":\"id:\\u5fc5\\u987b\\u662f\\u6574\\u6570;\\u6700\\u5c0f\\u503c\\u662f1;\\u6700\\u5927\\u503c\\u662f2147483647\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     *  test column detail api with geotable_id missing.
     */
    @Test
    public void testColumnDetailGeotableidMissing(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=142760";
        String expectedResult = "{\"status\":2,\"message\":\"geotable_id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     *  test column detail api with geotable_id not exist.
     */
    @Test
    public void testColumnDetailGeotableidNotExist(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=142760&geotable_id=106";
        String expectedResult = "{\"status\":1004,\"message\":\"geotable\\u4e0d\\u5b58\\u5728\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     *  test column detail api with geotable_id invalid.
     */
    @Test
    public void testColumnDetailGeotableidInvalid(){
        String url = this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=142760&geotable_id=abc";
        String expectedResult = "{\"status\":2,\"message\":\"geotable_id:\\u5fc5\\u987b\\u662f\\u6574\\u6570;\\u6700\\u5c0f\\u503c\\u662f1;\\u6700\\u5927\\u503c\\u662f2147483647\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test response status for poi list interface.
     */
    @Test
    public void testPoiListStatusLine()
    {
        HttpResponse response = this.getHttpResponse(RequestType.GET, this.domain + "/geodata/v3/column/detail?&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106970&id=142760");
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());
    }
    
    /**
     * test poi list interface.
     */
    @Test
    public void testPoiList()
    {
        String url = this.domain + "/geodata/v3/poi/list?geotable_id=106989&page_size=2&ak=Ow5fqi6DQXmgD5PGSB7QBdHF";
        String expectedResult = "{\"status\":0,\"size\":2,\"total\":312,\"pois\":[{\"title\":\"\\u4f1a\\u5c55\\u4e2d\\u5fc3\",\"location\":[117.73733,39.03566],\"city\":\"\\u5929\\u6d25\\u5e02\",\"create_time\":\"2015-06-12 10:59:40\",\"geotable_id\":106989,\"province\":\"\\u5929\\u6d25\\u5e02\",\"district\":\"\\u6ee8\\u6d77\\u65b0\\u533a\",\"metrotype2\":12,\"metrotype\":3,\"rgcurl\":\"http:\\/\\/tj.bendibao.com\\/ditie\\/zdhuizhanzhongxin.shtml\",\"city_id\":332,\"id\":933970361},{\"title\":\"\\u4e1c\\u6d77\\u8def\",\"location\":[117.743043,39.046771],\"city\":\"\\u5929\\u6d25\\u5e02\",\"create_time\":\"2015-06-12 10:59:40\",\"geotable_id\":106989,\"province\":\"\\u5929\\u6d25\\u5e02\",\"district\":\"\\u6ee8\\u6d77\\u65b0\\u533a\",\"metrotype2\":11,\"metrotype\":1,\"rgcurl\":\"http:\\/\\/tj.bendibao.com\\/ditie\\/zddonghailu.shtml\",\"city_id\":332,\"id\":933970356}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /***
     * test poi list sn verification.
     */
    @Test
    public void testPoiListSn(){
        
        String url = this.domain + "/geodata/v3/poi/list?geotable_id=106989&page_size=2&ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=af392e59dc277b4985a95af474b76591";
        String expectedResult = "{\"status\":0,\"size\":2,\"total\":312,\"pois\":[{\"title\":\"\\u4f1a\\u5c55\\u4e2d\\u5fc3\",\"location\":[117.73733,39.03566],\"city\":\"\\u5929\\u6d25\\u5e02\",\"create_time\":\"2015-06-12 10:59:40\",\"geotable_id\":106989,\"province\":\"\\u5929\\u6d25\\u5e02\",\"district\":\"\\u6ee8\\u6d77\\u65b0\\u533a\",\"metrotype2\":12,\"metrotype\":3,\"rgcurl\":\"http:\\/\\/tj.bendibao.com\\/ditie\\/zdhuizhanzhongxin.shtml\",\"city_id\":332,\"id\":933970361},{\"title\":\"\\u4e1c\\u6d77\\u8def\",\"location\":[117.743043,39.046771],\"city\":\"\\u5929\\u6d25\\u5e02\",\"create_time\":\"2015-06-12 10:59:40\",\"geotable_id\":106989,\"province\":\"\\u5929\\u6d25\\u5e02\",\"district\":\"\\u6ee8\\u6d77\\u65b0\\u533a\",\"metrotype2\":11,\"metrotype\":1,\"rgcurl\":\"http:\\/\\/tj.bendibao.com\\/ditie\\/zddonghailu.shtml\",\"city_id\":332,\"id\":933970356}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi list interface for default page size.
     */
    @Test
    public void testPoiListDefaultPageSize()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106989";
        Map<String, String> pairs = getResponsePairs(RequestType.GET, url);
        Assert.assertEquals("0", pairs.get("status"));
        Assert.assertEquals("312", pairs.get("total"));
        Assert.assertEquals("10", pairs.get("size"));
        Assert.assertEquals("10", pairs.get("pois#"));
    }
    
    /**
     * test poi list interface for page index couldn't be greater than 9.
     */
    @Test
    public void testPoiListMaxPageIndex()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106989&page_index=31&page_size=10";
        Assert.fail("bug #487");
    }
    
    /**
     * test poi list interface for page size couldn't be greater than 200.
     */
    @Test
    public void testPoiListMaxPageSize()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106989&page_index=0&page_size=301";
        Assert.fail("bug #487");
    }
    
    /**
     * test poi list interface using title parameter.
     */
    @Test
    public void testPoiListTitleParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106989&title=%E4%BC%9A%E5%B1%95%E4%B8%AD%E5%BF%83";
        String expectedResult = "{\"status\":0,\"size\":1,\"total\":1,\"pois\":[{\"title\":\"\\u4f1a\\u5c55\\u4e2d\\u5fc3\",\"location\":[117.73733,39.03566],\"city\":\"\\u5929\\u6d25\\u5e02\",\"create_time\":\"2015-06-12 10:59:40\",\"geotable_id\":106989,\"province\":\"\\u5929\\u6d25\\u5e02\",\"district\":\"\\u6ee8\\u6d77\\u65b0\\u533a\",\"metrotype2\":12,\"metrotype\":3,\"rgcurl\":\"http:\\/\\/tj.bendibao.com\\/ditie\\/zdhuizhanzhongxin.shtml\",\"city_id\":332,\"id\":933970361}],\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi list interface for tags parameter.
     * test the total poi recalled.
     */
    @Test
    public void testPoiListTagsParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106994&tags=beimeizhou";
        Map<String, String> pairs = getResponsePairs(RequestType.GET, url);
        Assert.assertEquals("0", pairs.get("status"));
        Assert.assertEquals("160", pairs.get("total"));
        Assert.assertEquals("10", pairs.get("size"));
        Assert.assertEquals("10", pairs.get("pois#"));
    }
    
    /**
     * test poi list interface for bounds parameter.
     * test the total poi recalled.
     */
    @Test
    public void testPoiListBoundsParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106990&bounds=116.364312,39.907408;116.436751,39.955432";
        Map<String, String> pairs = getResponsePairs(RequestType.GET, url);
        Assert.assertEquals("0", pairs.get("status"));
        Assert.assertEquals("207", pairs.get("total"));
        Assert.assertEquals("10", pairs.get("size"));
        Assert.assertEquals("10", pairs.get("pois#"));
    }
    
    /**
     * test poi list interface for string search_field parameter.
     * test the total poi recalled.
     */
    @Test
    public void testPoiListStringSearchFieldParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106990&category=%E5%9C%B0%E9%93%81";
        Map<String, String> pairs = getResponsePairs(RequestType.GET, url);
        Assert.assertEquals("0", pairs.get("status"));
        Assert.assertEquals("314", pairs.get("total"));
        Assert.assertEquals("10", pairs.get("size"));
        Assert.assertEquals("10", pairs.get("pois#"));
    }
    
    /**
     * test poi list interface for int search_field parameter.
     * test the total poi recalled.
     */
    @Test
    public void testPoiListIntSearchFieldParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106990&metrotype=1,2";
        Map<String, String> pairs = getResponsePairs(RequestType.GET, url);
        Assert.assertEquals("0", pairs.get("status"));
        Assert.assertEquals("2892", pairs.get("total"));
        Assert.assertEquals("10", pairs.get("size"));
        Assert.assertEquals("10", pairs.get("pois#"));
    }
    
    /**
     * test poi list interface for double search_field parameter.
     * test the total poi recalled.
     */
    @Test
    public void testPoiListDoubleSearchFieldParameter()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106994&star=3,5";
        Assert.fail("bug #488 in icafe api-bugs");
    }
    
    /**
     * test poi list interface headers.
     */
    @Test
    public void testPoiListHeaders()
    {
        String url = this.domain + "/geodata/v3/poi/list?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&geotable_id=106989&page_size=2";
        HttpResponse response = this.getHttpResponse(RequestType.GET, url);
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
    }
    
    /**
     * test poi detail for required parameters.
     */
    @Test
    public void testPoiDetail(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366264";
        String expectedResult = "{\"status\":0,\"poi\":{\"title\":\"\\u7eb3\\u6c99\\u5c14\\u96d5\\u523b\\u4e2d\\u5fc3\",\"location\":[-96.800473,32.787996],\"city\":\"\",\"create_time\":\"2015-06-16 20:03:01\",\"geotable_id\":106994,\"tags\":\"beimeizhou meiguo dalasi  7001 7003 7006 504\",\"province\":\"\",\"district\":\"\",\"icon_style_id\":\"sid1\",\"star\":3,\"desc\":\"\\u7eb3\\u6c99\\u5c14\\u96d5\\u523b\\u4e2d\\u5fc3\\u5185\\u6709\\u5404\\u79cd\\u5404\\u6837\\u7684\\u827a\\u672f\\u4f5c\\u54c1\\uff0c\\u4ee5\\u96d5\\u5851\\u4e3a\\u4e3b\\u3002\",\"score\":0,\"scene_layer\":6,\"gone_count\":0,\"going_count\":0,\"view_count\":0,\"city_id\":0,\"id\":949366264},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi detail for required parameters.
     */
    @Test
    public void testPoiDetailSn(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&id=949366264&ak=maI6LFBKDhbzR8MMgx0x1PoL&sn=3bca3d3c9bce2a4f58d6916d207abe46";
        String expectedResult = "{\"status\":0,\"poi\":{\"title\":\"\\u7eb3\\u6c99\\u5c14\\u96d5\\u523b\\u4e2d\\u5fc3\",\"location\":[-96.800473,32.787996],\"city\":\"\",\"create_time\":\"2015-06-16 20:03:01\",\"geotable_id\":106994,\"tags\":\"beimeizhou meiguo dalasi  7001 7003 7006 504\",\"province\":\"\",\"district\":\"\",\"icon_style_id\":\"sid1\",\"star\":3,\"desc\":\"\\u7eb3\\u6c99\\u5c14\\u96d5\\u523b\\u4e2d\\u5fc3\\u5185\\u6709\\u5404\\u79cd\\u5404\\u6837\\u7684\\u827a\\u672f\\u4f5c\\u54c1\\uff0c\\u4ee5\\u96d5\\u5851\\u4e3a\\u4e3b\\u3002\",\"score\":0,\"scene_layer\":6,\"gone_count\":0,\"going_count\":0,\"view_count\":0,\"city_id\":0,\"id\":949366264},\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi detail for headers.
     */
    @Test
    public void testPoiDetailHeaders(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366264";
        HttpResponse response = this.getHttpResponse(RequestType.GET, url);
        Assert.assertEquals("text/javascript; charset=UTF-8", response.getFirstHeader("Content-Type").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Error-Code").getValue());
        Assert.assertEquals("map", response.getFirstHeader("Http_x_bd_product").getValue());
        Assert.assertEquals("apimap", response.getFirstHeader("Http_x_bd_subsys").getValue());
        Assert.assertEquals("0", response.getFirstHeader("Hulu-Id").getValue());
        Assert.assertEquals("Accept-Encoding", response.getFirstHeader("Vary").getValue());
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid64"));
        Assert.assertNotNull(response.getFirstHeader("Http_x_bd_logid"));
    }
    
    /**
     * test poi detail for required parameters.
     */
    @Test
    public void testPoiDetailStatusLine(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366264";
        HttpResponse response = this.getHttpResponse(RequestType.GET, url);
        Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString()); 
    }
    
    /**
     * test poi detail for id missing.
     */
    @Test
    public void testPoiDetailIdMissing(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF";
        String expectedResult = "{\"status\":2,\"message\":\"id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi detail for geotable_id missing.
     */
    @Test
    public void testPoiDetailGeotableIdMissing(){
        String url = this.domain + "/geodata/v3/poi/detail?ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366264";
        String expectedResult = "{\"status\":2,\"message\":\"geotable_id:\\u53c2\\u6570\\u5fc5\\u9700\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi detail for geotable_id invalid.
     */
    @Test
    public void testPoiDetailGeotableIdInvalid(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=102994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366264";
        String expectedResult = "{\"status\":1004,\"message\":\"geotable\\u4e0d\\u5b58\\u5728\"}";
        verifyResponse(url, expectedResult);
    }
    
    /**
     * test poi detail for id invalid.
     */
    @Test
    public void testPoiDetailIdInvalid(){
        String url = this.domain + "/geodata/v3/poi/detail?geotable_id=106994&ak=Ow5fqi6DQXmgD5PGSB7QBdHF&id=949366265";
        String expectedResult = "{\"status\":0,\"poi\":null,\"message\":\"\\u6210\\u529f\"}";
        verifyResponse(url, expectedResult);
    }
    
}
