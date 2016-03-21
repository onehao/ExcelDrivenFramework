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
package com.sapi.apitest.datadriven.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sapi.apitest.datadriven.ExcelUtils;

/***
 * The class encapsulate the test properties respected to the excel cell or
 * column where to read the tests.
 * 
 * @author wanhao01
 * 
 *
 */

public class ExcelDrivenTestContext {

    private Properties properties;

    // pattern used to extract the value from ****(value)****
    private static final Pattern PATTERN = Pattern.compile("[\\(].*[\\)]");

    private final String ownerPropertyString = "testcase.owner";
    private final String releaseVersionPropertyString = "testcase.releaseVersion";
    private final String apiVersionPropertyString = "testcase.apiVersion";
    private final String caseVersionPropertyString = "testcase.caseVersion";
    private final String apiNamePropertyString = "testcase.apiName";
    private final String domainAPropertyString = "testcase.domainA";
    private final String domainBPropertyString = "testcase.domainB";

    private final String startlinePropertyString = "testcase.startline";
    private final String requestTypePropertyString = "testcase.requestType";
    private final String testURLPropertyString = "testcase.testURL";
    private final String priorityPropertyString = "testcase.priority";
    private final String categoryPropertyString = "testcase.category";
    private final String descriptionPropertyString = "testcase.description";
    private final String expectedResultPropertyString = "testcase.expectedResult";
    private final String actualResultPropertyString = "testcase.actualResult";
    private final String ignorePropertyString = "testcase.isIgnore";
    private final String fastFailPropertyString = "testcase.isFastFail";
    private final String bugUrlPropertyString = "testcase.bugURL";
    private final String testResultPropertyString = "testcase.result";
    private final String worksheetPropertyString = "testcase.worksheets";

    private final String errorWorksheetNotExist = "the specified worksheet with the name {} doesn't exist.";

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    public ExcelDrivenTestContext(Properties properties) {
        loadProperties(properties);
    }

    /**
     * Load the specific properities.
     * @param properties
     */
    public void loadProperties(Properties properties) {
        this.properties = properties;
        this.worksheets = new LinkedList<String>();
        initGlobalContext();
        initTestcaseContext();
    }

    /**
     * Global context is the value that read directly from a cell value, the
     * value is usually used for all the testcases as a context.
     */
    private void initGlobalContext() {
        this.excelFileLocation = this.properties.getProperty(
                "testcase.excelFileLocation", "");
        this.workbook = getWorkbook(this.excelFileLocation);

        this.owner = getCellValue(
                this.properties.getProperty(ownerPropertyString, "Michael Wan"),
                this.workbook);
        this.releaseVersion = getCellValue(
                this.properties.getProperty(releaseVersionPropertyString, ""),
                this.workbook);
        this.apiVersion = getCellValue(
                this.properties.getProperty(apiVersionPropertyString, ""),
                this.workbook);
        this.caseVersion = getCellValue(
                this.properties.getProperty(caseVersionPropertyString, ""),
                this.workbook);
        this.apiName = getCellValue(
                this.properties.getProperty(apiNamePropertyString, ""),
                this.workbook);
        this.domainA = getCellValue(
                this.properties.getProperty(domainAPropertyString, ""),
                this.workbook);
        this.domainB = getCellValue(
                this.properties.getProperty(domainBPropertyString, ""),
                this.workbook);
    }

    /**
     * Test case context are used usually as a column, as the framework will
     * read iterated row by row, and the values of each row will be used to
     * construct each test case.
     */
    private void initTestcaseContext() {
        this.startLine = Integer.parseInt(this.properties.getProperty(
                startlinePropertyString, "1"));
        this.requestType = this.properties.getProperty(
                requestTypePropertyString, "GET");
        this.testURL = this.properties.getProperty(testURLPropertyString, "");
        this.priority = this.properties
                .getProperty(priorityPropertyString, "2");
        this.category = this.properties.getProperty(categoryPropertyString, "");
        this.description = this.properties.getProperty(
                descriptionPropertyString, "");

        this.expectedResult = this.properties.getProperty(
                expectedResultPropertyString, "");
        this.actualResult = this.properties.getProperty(
                actualResultPropertyString, "");
        this.isIgnore = this.properties.getProperty(ignorePropertyString, "NO");
        this.isFastFail = this.properties.getProperty(fastFailPropertyString,
                "NO");
        this.bugURL = this.properties.getProperty(bugUrlPropertyString, "");
        this.result = this.properties.getProperty(testResultPropertyString, "");

        try {
            Collections.addAll(this.worksheets,
                    this.properties.getProperty(worksheetPropertyString, "")
                            .split("\\|"));
        } catch (Exception e) {
            logger.error("{}\n{}", e.getMessage(), e.getStackTrace());
        }
    }

    public static void main(String[] args) {
        String cellID = "SHEET(overview).C4";

        Matcher m = PATTERN.matcher(cellID);
        if (m.find()) {
            int start = m.start();
            int end = m.end();
            System.out.println(cellID.substring(start + 1, end - 1));
        } 
    }

    /**
     * Get string value from a Cell and if not a cell, then reading as a raw
     * string.
     * 
     * @param cellID
     * @param wb
     * @return
     */
    private String getCellValue(String cellID, Workbook wb) {
        if (cellID == null | cellID.length() <= 0) {
            return "";
        }
        Sheet sheet = null;
        int rowID = 1;
        int columnID = 1;
        // if using the SHEET to read from the specific sheet.
        if (cellID.toUpperCase().startsWith("SHEET")) {
            Matcher m = PATTERN.matcher(cellID);
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                sheet = wb.getSheet(cellID.substring(start + 1, end - 1));
            }
            cellID = cellID.substring(cellID.indexOf(".") + 1);
            if (sheet == null) {
                logger.error(errorWorksheetNotExist);
                return "";
            }
        } else {
            // 没有指定worksheet，默认从第一个个worksheet读取
            sheet = wb.getSheetAt(0);
        }

        // in this case the the value is not a cell but a string value specified
        // directly.
        if (!ExcelUtils.isCell(cellID)) {
            return cellID;
        }

        rowID = Integer.parseInt(cellID.substring(1)) - 1;
        // in this case we don't support excel that has a column more than 26,
        // such as AA, AAA.
        columnID = cellID.substring(0, 1).toUpperCase().toCharArray()[0] - 'A';
        Row row = sheet.getRow(rowID);
        Cell cell = row.getCell(columnID);

        return cell.getStringCellValue();
    }

    /**
     * Construct the Excel Workbook entity from the specific excel location.
     * 
     * @param excelFileLocation
     * @return
     */
    private Workbook getWorkbook(String excelFileLocation) {
        InputStream in = null;
        Workbook wb = null;
        Throwable exception = null;
        try {
            in = new FileInputStream(excelFileLocation);
            try {
                wb = WorkbookFactory.create(in);
            } catch (EncryptedDocumentException | InvalidFormatException
                    | IOException e) {
                exception = e;
            }
        } catch (FileNotFoundException e) {
            exception = e;
        } finally {
            if (null != exception) {
                logger.error("{}\n{}", exception.getMessage(),
                        exception.getStackTrace());
            }
        }
        return wb;
    }
    

    /**
     * @return the workbook
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    private int startLine;

    /**
     * @return the startLine
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @return the testURL
     */
    public String getTestURL() {
        return testURL;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the expectedResult
     */
    public String getExpectedResult() {
        return expectedResult;
    }

    /**
     * @return the isIgnore
     */
    public String getIsIgnore() {
        return isIgnore;
    }

    /**
     * @return the isFastFail
     */
    public String getIsFastFail() {
        return isFastFail;
    }

    /**
     * @return the bugURL
     */
    public String getBugURL() {
        return bugURL;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return the releaseVersion
     */
    public String getReleaseVersion() {
        return releaseVersion;
    }

    /**
     * @return the caseVersion
     */
    public String getCaseVersion() {
        return caseVersion;
    }

    /**
     * @return the apiName
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * @return the excelFileLocation
     */
    public String getExcelFileLocation() {
        return excelFileLocation;
    }

    /**
     * 
     * @return the worksheets
     */
    public List<String> getWorksheets() {
        return worksheets;
    }

    private String requestType;

    private String testURL;

    private String priority;

    private String category;

    private String description;

    private String expectedResult;

    private String actualResult;

    private String isIgnore;

    private String isFastFail;

    private String bugURL;

    private String result;

    private String owner;

    private String releaseVersion;

    private String caseVersion;

    private String apiName;

    private String apiVersion;

    private String domainA;

    /**
     * @return the domainA
     */
    public String getDomainA() {
        return domainA;
    }

    /**
     * @return the domainB
     */
    public String getDomainB() {
        return domainB;
    }

    private String domainB;

    /**
     * @return the apiVersion
     */
    public String getApiVersion() {
        return apiVersion;
    }

    private String excelFileLocation;

    private List<String> worksheets;

    private Workbook workbook;

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }
}