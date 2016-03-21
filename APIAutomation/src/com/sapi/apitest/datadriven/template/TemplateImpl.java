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
package com.sapi.apitest.datadriven.template;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Template Strategy implementation.
 * @author wanhao01
 *
 */
public class TemplateImpl extends Template {

    private static final Logger logger = LogManager.getLogger(Thread
            .currentThread().getStackTrace()[1].getClassName());

    /**
     * The report body implementation.
     */
    @Override
    protected void operation(BufferedWriter bwriter, String content) {
        try {
            bwriter.write(content);
        } catch (IOException e) {
            logger.error(e);
        }
        this.content.append(content);
    }
}