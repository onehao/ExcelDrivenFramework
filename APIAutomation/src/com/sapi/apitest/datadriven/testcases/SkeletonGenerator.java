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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sapi.apitest.datadriven.ExcelUtils;
import com.sapi.apitest.datadriven.utils.HttpRequest;
import com.sapi.apitest.datadriven.utils.HttpRequest.RequestType;

/**
 * This class used to generate test case skeleton especially for expected
 * result.
 * 
 * @author wanhao01
 *
 */
public class SkeletonGenerator {
    
    private final int testUrlRowNum = 3;
    private final int expectedResultRowNum = 5;
    
    private final String excelPath = "D:\\workspace\\satp2\\APIAutomation\\src\\com\\sapi\\"
                + "apitest\\datadriven\\testcases\\Test-case-geosearch.xls";

    /**
     * generate expected result to the excel document.
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws IOException
     */
    public void getExpectedResult() throws EncryptedDocumentException, InvalidFormatException, IOException {
        InputStream inp = new FileInputStream(
                excelPath);

        String[] sheets = new String[] { "nearby", "bounds", "local" };

        Workbook wb = WorkbookFactory.create(inp);
        
        String testcaseUrl = "";
        String response = null;
        for (String sheetName : sheets) {
            Sheet sheet = wb.getSheet(sheetName);
            
            for (int rowNum = 1; !ExcelUtils.isEnd(sheet, rowNum,
                    'B'); rowNum++) {
                try {
                    Row row = sheet.getRow(rowNum);
                    testcaseUrl = ExcelUtils.getCellValueAsString(sheet, row, testUrlRowNum);
                    if (testcaseUrl == null || testcaseUrl.isEmpty()) {
                        continue;
                    }
                    response = HttpRequest.sendGet(testcaseUrl);
                    
                    Cell cell = row.getCell(expectedResultRowNum);
                    if (cell == null) {
                        cell = row.createCell(expectedResultRowNum);
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(response);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println(e.getMessage());
                }
            }
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(excelPath);
        wb.write(fileOut);
        fileOut.close();
    }
    
    public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
        new SkeletonGenerator().getExpectedResult();
    }
}