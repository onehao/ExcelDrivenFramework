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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.annotations.TestCaseAttribute;
import com.sapi.apitest.datadriven.entity.ExcelDrivenTestCase;

/***
 * 
 * Construct/Reflect the test cases.
 * @author wanhao01
 * 
 *
 */
public class ExcelTestcaseReflector {

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    static List<Field> attributes = new LinkedList<Field>();

    /**
     * Get the field with the annotation ExcelDrivenTestCase.
     * @return
     */
    public static List<Field> getTestCaseAttribute() {
        attributes = getAnnotationAttributes(ExcelDrivenTestCase.class,
                TestCaseAttribute.class);
        return attributes;
    }

    /**
     * Reflect and get the field with the specific annotation.
     * @param entityClass
     * @param annotationClass
     * @return
     */
    private static <T extends Annotation> List<Field> getAnnotationAttributes(
            Class<?> entityClass, Class<T> annotationClass) {
        List<Field> attributes = new LinkedList<Field>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(annotationClass) != null) {
                attributes.add(field);
            }
        }
        return attributes;
    }

}
