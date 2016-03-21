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
package com.sapi.apitest.datadriven.geodata;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/***
 * The unit test execution for creating the POIs for geosearch.
 * 
 * @author wanhao01
 *
 */
public class GeodataBatchImportTest {

    private GeodataBatchImport importer;
    String path = this.getClass().getResource("").getPath();

    @Before
    public void setUp() throws Exception {
        importer = GeodataBatchImport.getInstance();

        System.out.println(path);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExportPOIFromCsvFile() {
        String filePath = path + "/geodatainitTable12.csv";
        try {
            importer.exportPOIFromCsvFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testExportPOIFromCsvOnline() {
        fail("Not yet implemented");
    }
}