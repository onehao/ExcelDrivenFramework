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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sapi.apitest.datadriven.context.CSVContext;
import com.sapi.apitest.datadriven.context.TestDataGenerationContext;
import com.sapi.apitest.datadriven.utils.HttpRequest;

/***
 * 
 * @author wanhao01
 * @since 2015-06-08
 * @description: batch import the POI data, and write back the POI id to the csv
 *               file.
 * 
 */

public class GeodataBatchImport {

    private static final String USER_DIR = "user.dir";

    private static final String CONFIG_SAPI_PROPERTIES = "/config/sapi.properties";

    private static GeodataBatchImport INSTANCE = new GeodataBatchImport();

    static String clazz = Thread.currentThread().getStackTrace()[1]
            .getClassName();

    static final Logger logger = LogManager.getLogger(clazz);

    private static final String SEPARATOR = System
            .getProperty("line.separator");

    private static final String COL_NAME_POIID = "poiID"; // add column of
                                                            // poiID.

    private Properties properties;

    private static int importCount = 0;

    private TestDataGenerationContext testGenerationContext;

    private CSVContext csvContext;
    
    private GeodataBatchImport() {
        
        properties = new Properties();
        try {
            properties.load(new FileReader(System.getProperty(USER_DIR)
                    + CONFIG_SAPI_PROPERTIES));
            testGenerationContext = new TestDataGenerationContext(properties);
            csvContext = new CSVContext(properties);
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    public static GeodataBatchImport getInstance() {
        return INSTANCE;
    }

    /**
     * Export the local csv file, only local file support write back.
     * 
     * @param filePath
     * @throws IOException
     * 
     */
    public void exportPOIFromCsvFile() throws IOException {

        // Reader in = new FileReader(csvContext.getInputLocation());
        Reader in = new InputStreamReader(new FileInputStream(
                csvContext.getInputLocation()), csvContext.getEncoding());
        exportPOIFromCsv(in);

    }

    /**
     * Export the csv file from web.
     * 
     * @param url
     * @throws IOException
     */
    public void exportPOIFromCsvOnline(URL url) throws IOException {
        Reader reader = new InputStreamReader(url.openStream(),
                csvContext.getEncoding());
        exportPOIFromCsv(reader);
    }

    /**
     * Export POI from CSV.
     * @param reader
     * @throws IOException
     */
    private void exportPOIFromCsv(Reader reader) throws IOException {
        CSVParser parser = null;
        CSVPrinter printer = null;
        String jsonContent = "";
        try {
            parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());

            LinkedHashMap<String, Integer> header = (LinkedHashMap<String, Integer>) parser
                    .getHeaderMap();

            StringBuilder builder;

            long poiID;
            String url = testGenerationContext.getRequestUrl()
                    + testGenerationContext.getRequestContext();
            Writer writer = new OutputStreamWriter(new FileOutputStream(
                    csvContext.getOutputLocation()), csvContext.getEncoding());
            List<String> oCsvHeaders = new ArrayList<String>(
                    Arrays.asList((header.keySet()).toArray(new String[0])));
            oCsvHeaders.add(COL_NAME_POIID);
            printer = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withRecordSeparator(SEPARATOR));
            printer.printRecord(oCsvHeaders);
            for (CSVRecord record : parser) {
                builder = new StringBuilder();
                for (String key : header.keySet()) {
                    if (!record.get(key).isEmpty()) {
                        builder.append(key + "=" + record.get(key) + "&");
                    }
                }

                builder.deleteCharAt(builder.length() - 1);

                // TODO:deal with the http request with the url
                jsonContent = HttpRequest.doPost(url, builder.toString());

                // TODO: parsing the response and get the data needed.
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonContent);
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    logger.error(e1.getMessage());
                    continue;
                }
                try {
                    poiID = jsonObject.getLong("id");
                } catch (JSONException e) {
                    logger.error("{} {} {}", "error getting id, content: -- ",
                            jsonContent);
                    continue;
                }

                // TODO: write the data to the output csv, such and the ids of
                // the create operation.

                try {
                    writeBack(printer, record, oCsvHeaders, poiID);
                } catch (Exception e) {
                    logger.error("{} \n {} \n {}", e.getMessage(),
                            e.getStackTrace());
                }

                logger.info(builder.toString());

                importCount++;
            }
        } catch (IOException e) {
            logger.error("{} \n {} \n {}", e.getMessage(), jsonContent,
                    e.getStackTrace());

        } finally {
            logger.info("totally imported number: " + importCount);

            if (null != printer) {
                printer.close();
            }
            if (!parser.isClosed()) {
                parser.close();
            }
        }
    }

    /**
     * The function used when we want to write back the result in the excel
     * files.
     * 
     * @param printer
     * @param record
     * @param oCsvHeaders
     * @param poiID
     * @throws IOException
     */
    private void writeBack(CSVPrinter printer, CSVRecord record,
            List<String> oCsvHeaders, long poiID) throws IOException {

        List<String> oCsvRecord = record2list(record, oCsvHeaders, poiID);
        printer.printRecord(oCsvRecord);
    }

    /**
     * Reconstruct the record to list.
     * 
     * @param record
     * @param oCvsHeaders
     * @param poiID
     * @return
     */
    private List<String> record2list(CSVRecord record,
            List<String> oCvsHeaders, long poiID) {
        List<String> cvsRecord;
        Map<String, String> rMap = record.toMap();
        long recNo = record.getRecordNumber();
        rMap = alterRecord(rMap, recNo);
        rMap.put(COL_NAME_POIID, String.valueOf(poiID));
        cvsRecord = new ArrayList<String>();
        for (String key : oCvsHeaders) {
            cvsRecord.add(rMap.get(key));
        }
        return cvsRecord;
    }

    /**
     * Reorder the object.
     * 
     * @param rMap
     * @param recNo
     * @return
     */
    private Map<String, String> alterRecord(Map<String, String> rMap, long recNo) {
        int rv;
        Random rg = new Random(recNo);
        rv = rg.nextInt(50);
        String[] ks = rMap.keySet().toArray(new String[0]);
        int ix = rg.nextInt(ks.length);
        // ix = 1;// overwrite and get the coord type as constant.
        while (!isLong(rMap.get(ks[ix]))) {
            ix = rg.nextInt(ks.length);
        }
        long yv = 0;
        String ky = ks[ix];
        String xv = rMap.get(ky);

        if (xv != null && xv.length() > 0) {
            yv = Long.valueOf(xv) + rv;
            rMap.put(ks[ix], String.valueOf(yv));
        }
        return rMap;
    }

    /**
     * Check whether the input value count convert to long type.
     * 
     * @param value
     * @return
     */
    private boolean isLong(String value) {

        try {
            long longValue = Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            logger.error("{} {}", "couldn't parse to long for the value: ",
                    value);
            logger.error("{} \n {}", e.getMessage(), e.getStackTrace());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty(USER_DIR));
    }
}