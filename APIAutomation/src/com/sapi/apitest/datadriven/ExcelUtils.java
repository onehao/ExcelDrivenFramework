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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sapi.apitest.datadriven.context.ExcelDrivenTestContext;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;

/***
 * Utility class that encapsulate the logic for test cases construction,
 * execution.
 * 
 * @author wanhao01 
 * 
 *
 */
public class ExcelUtils {
    private static final Logger logger = LogManager.getLogger(Thread
            .currentThread().getStackTrace()[1].getClassName());
    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";
    private static String category = "";
    private static final String CATEGORY_HEADER = "CATEGORY";

    /**
     * Parsing test cases from a single worksheet, and return the test case.
     * list.
     * 
     * @param context
     * @param sheet
     * @return
     */
    public static List<ExcelDrivenTestCase> getExcelDrivenTestCases(
            ExcelDrivenTestContext context, Sheet sheet) {
        List<ExcelDrivenTestCase> testcases = new LinkedList<ExcelDrivenTestCase>();
        ExcelDrivenTestCase testcase;
        for (int rowNum = context.getStartLine() - 1; !isEnd(sheet, rowNum,
                context); rowNum++) {
            testcase = getTestcaseFromLine(rowNum, sheet, context);
            if (null != testcase) {
                testcases.add(testcase);
            }
        }
        return testcases;
    }

    /**
     * Check whether the reading of the excel need to be finished. in this story
     * use two blank line to determine stop.
     * 
     * @param sheet
     * @param rowNum
     * @return
     */
    private static boolean isEnd(Sheet sheet, int rowNum,
            ExcelDrivenTestContext context) {
        // as description for a test case is a required field, we use it for
        // checking end.
        // when reach 2 continuous line in hierarchy is blank or null then stop
        // parsing test cases.
        Row row = sheet.getRow(rowNum);
        int column = context.getDescription().toUpperCase().toCharArray()[0] - 'A';
        Cell cell = row.getCell(column);
        Cell nextCell;
        if (cell.getStringCellValue() == null
                || cell.getStringCellValue().length() <= 0) {
            nextCell = sheet.getRow(rowNum + 1).getCell(column);
            if (nextCell.getStringCellValue() == null
                    || nextCell.getStringCellValue().length() <= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check whether the reading of the excel need to be finished. in this story
     * use two blank line to determine stop.
     * 
     * @param sheet
     * @param rowNum
     * @return
     */
    public static boolean isEnd(Sheet sheet, int rowNum,
            char columnChar) {
        // as description for a test case is a required field, we use it for
        // checking end.
        // when reach 2 continuous line in hierarchy is blank or null then stop
        // parsing test cases.
        Row row = sheet.getRow(rowNum);
        int column = columnChar - 'A';
        Cell cell = row.getCell(column);
        Cell nextCell;
        if (cell.getStringCellValue() == null
                || cell.getStringCellValue().length() <= 0) {
            nextCell = sheet.getRow(rowNum + 1).getCell(column);
            if (nextCell.getStringCellValue() == null
                    || nextCell.getStringCellValue().length() <= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check whether the string value cellID is a valid excel cell id. if this
     * function we used a brute way to deal with the value, the fast fail is
     * when the row isn't a string, then we consider this value isn't a cell id,
     * and we don't support the column value that greater than Z, as for excel
     * we support "AA", "AAA", but in the test we usually don't have that much
     * properties, and the framework is currently used internally, will add this
     * support only when needed.
     * 
     * @param cellID
     * @return
     */
    public static boolean isCell(String cellID) {
        boolean isCell = true;
        try {
            Integer.parseInt(cellID.substring(1));

        } catch (NumberFormatException e) {
            isCell = false;
        }
        return isCell;
    }

    /**
     * Construct test case from a single line.
     * 
     * @param rowNum
     * @param sheet
     * @param context
     * @return
     */
    private static ExcelDrivenTestCase getTestcaseFromLine(int rowNum,
            Sheet sheet, ExcelDrivenTestContext context) {
        ExcelDrivenTestCase testcase = new ExcelDrivenTestCase();
        Row row = sheet.getRow(rowNum);
        List<Field> fields = ExcelTestcaseReflector.getTestCaseAttribute();

        for (Field field : fields) {
            try {
                Method fieldContextGetterMethod = ExcelDrivenTestContext.class
                        .getDeclaredMethod(retriveGetterForField(field));
                Method fieldTestcaseSetterMethod = ExcelDrivenTestCase.class
                        .getMethod(retriveSetterForField(field), String.class);

                try {
                    // get the excel column where to read the attribute.
                    String column = (String) fieldContextGetterMethod
                            .invoke(context);
                    if (column == null | column.length() <= 0) {
                        continue;
                    }
                    String value = column;
                    value = getCellValueAsString(sheet, row, column
                            .toUpperCase().toCharArray()[0] - 'A');
                    if (field.getName().toUpperCase().equals(CATEGORY_HEADER)) {
                        if (value.length() <= 0) {
                            value = category;
                        } else {
                            category = value;
                        }
                    }
                    // set testcase attribute
                    // TODO: we don't need parsing global attribute every time
                    fieldTestcaseSetterMethod.invoke(testcase, value);

                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    logger.error("{}\n{}", e.getMessage(), e.getStackTrace());
                }
            } catch (NoSuchMethodException | SecurityException e) {
                logger.error("{}\n{}", e, e.getStackTrace());
            }
        }

        // by convention, the description shouldn't be null, we use this key to
        // check the testcase.
        if (testcase.getDescription() == null
                || testcase.getDescription().length() <= 0) {
            return null;
        }
        testcase.setLine(String.format("%s_line:%s", sheet.getSheetName(),
                rowNum + 1));
        return testcase;
    }

    /**
     * Getting value as string from excel cell, currently we only support values
     * of <String, numeric, boolean>
     * 
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getCellValueAsString(Sheet sheet, Row row, int column) {
        Cell cell = row.getCell(column);
        String value = "";
        if (cell == null) {
            return value;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                // System.out.println(cell.getRichStringCellValue().getString());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue().toString();
                } else {
                    value = cell.getNumericCellValue() + "";
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            // case Cell.CELL_TYPE_FORMULA:
            // System.out.println(cell.getCellFormula());
            // break;
            default:
                try {
                    value = cell.getStringCellValue();
                } catch (Exception e) {
                    logger.error("{}\n{}\n{}",
                            "the cell value type is not currently supported.",
                            e.getMessage(), e.getStackTrace());
                }
        }
        return value;
    }

    /**
     * Retrieve the Getter method for the specific field.
     * 
     * @param field
     * @return
     */
    private static String retriveGetterForField(Field field) {
        return retrieveMethodForField(field, GETTER_PREFIX);
    }

    /**
     * Retrieve the Setter method for the specific field.
     * 
     * @param field
     * @return
     */
    private static String retriveSetterForField(Field field) {
        return retrieveMethodForField(field, SETTER_PREFIX);
    }

    /**
     * Retrieve the method with the specific prefix.
     * @param field
     * @param prefix
     * @return
     */
    private static String retrieveMethodForField(Field field, String prefix) {
        String fieldName = field.getName();
        String method = prefix + Character.toUpperCase(fieldName.charAt(0))
                + fieldName.substring(1);
        return method;
    }
}