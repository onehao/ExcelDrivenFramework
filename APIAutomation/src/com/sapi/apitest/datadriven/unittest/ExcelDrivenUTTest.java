package com.sapi.apitest.datadriven.unittest;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sapi.apitest.datadriven.ExcelDrivenUT;
import com.sapi.apitest.datadriven.ExcelUtils;
import com.sapi.apitest.datadriven.context.ExcelDrivenTestContext;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;

/**
 * Unit test for the excel driven test framework.
 * 
 * @author wanhao01
 *
 */
public class ExcelDrivenUTTest {

    private static String userDir = "";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        userDir = System.getProperty("user.dir")
                + "/bin/com/sapi/apitest/datadriven/unittest/";
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private ExcelDrivenTestContext getContext(ExcelDrivenUT ut) {
        Field[] fields = ut.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("context")) {
                field.setAccessible(true);
                try {
                    return (ExcelDrivenTestContext) field.get(ut);
                } catch (IllegalArgumentException | IllegalAccessException ignore) {
                    // ignore
                }
            }
        }
        return null;
    }

    // test cases size got from each worksheet is correct.
    @Test
    public void test() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test1.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Workbook wb = context.getWorkbook();
        Sheet sheet = null;

        // sheet 0
        List<ExcelDrivenTestCase> testcases = new LinkedList<ExcelDrivenTestCase>();
        sheet = wb.getSheet(context.getWorksheets().get(0));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(93, testcases.size());

        // sheet 1
        testcases = new LinkedList<ExcelDrivenTestCase>();
        sheet = wb.getSheet(context.getWorksheets().get(1));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(79, testcases.size());

        // sheet 2
        testcases = new LinkedList<ExcelDrivenTestCase>();
        sheet = wb.getSheet(context.getWorksheets().get(2));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(93, testcases.size());
    }

    // worksheet size is correct.
    @Test
    public void testCount() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test1.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Assert.assertEquals(3, context.getWorksheets().size());
    }

    // single worksheet size.
    @Test
    public void testCount2() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test2.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Assert.assertEquals(1, context.getWorksheets().size());
    }

    // test end mark.
    @Test
    public void testendmark() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test3.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Workbook wb = context.getWorkbook();
        // sheet 0
        List<ExcelDrivenTestCase> testcases = new LinkedList<ExcelDrivenTestCase>();
        Sheet sheet = wb.getSheet(context.getWorksheets().get(0));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(11, testcases.size());
    }

    // test null description.
    @Test
    public void testNullDescription() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test3.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Workbook wb = context.getWorkbook();
        // sheet 0
        List<ExcelDrivenTestCase> testcases = new LinkedList<ExcelDrivenTestCase>();
        Sheet sheet = wb.getSheet(context.getWorksheets().get(1));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(77, testcases.size());
    }

    // test global attribute.
    @Test
    public void testGlobalAttribute() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test3.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Assert.assertEquals(2, context.getStartLine());
        Assert.assertEquals("Geosearch", context.getApiName());
        Assert.assertEquals("api.map.baidu.com", context.getDomainA());
        Assert.assertEquals("Michael Wan", context.getOwner());
        Assert.assertEquals("V3", context.getReleaseVersion());
        Assert.assertEquals("V1", context.getCaseVersion());
    }

    // test DBCS.
    @Test
    public void testDBCSWorksheetName() {
        ExcelDrivenUT ut = new ExcelDrivenUT(userDir + "test3.properties");
        ExcelDrivenTestContext context = getContext(ut);
        Workbook wb = context.getWorkbook();
        // sheet 0
        List<ExcelDrivenTestCase> testcases = new LinkedList<ExcelDrivenTestCase>();
        Sheet sheet = wb.getSheet(context.getWorksheets().get(2));
        testcases = ExcelUtils.getExcelDrivenTestCases(context, sheet);
        Assert.assertEquals(93, testcases.size());
    }
}
