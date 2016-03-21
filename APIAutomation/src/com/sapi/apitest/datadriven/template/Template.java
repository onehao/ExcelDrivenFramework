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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/***
 * The report template class.
 * @author wanhao01
 *
 */
public abstract class Template {

    protected StringBuilder content = new StringBuilder();

    /**
     * @return the content
     */
    public StringBuilder getContent() {
        return content;
    }

    /**
     * Set the header of the template.
     * @param bwriter
     */
    private void beforeOperation(BufferedWriter bwriter) {
//        readTemplate(bwriter, System.getProperty("user.dir")
//                + "/com/sapi/apitest/datadriven/template/header.tpl");
    	readTemplate(bwriter, getClass().getResourceAsStream("/com/sapi/apitest/datadriven/template/header.tpl"));
    }

    /**
     * Read template.
     * @param bwriter
     * @param location
     */
    private void readTemplate(BufferedWriter bwriter, InputStream location) {
        BufferedReader reader = null;
        String input = "";
        try {
            reader = new BufferedReader(new InputStreamReader(location));
            while ((input = reader.readLine()) != null) {
                content.append(input);
                bwriter.write(input + "\r\n");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    /**
     * Set the footer of the template.
     * @param bwriter
     */
    private void afterOperation(BufferedWriter bwriter) {
//        readTemplate(bwriter, System.getProperty("user.dir")
//                + "/com/sapi/apitest/datadriven/template/footer.tpl");
        readTemplate(bwriter, getClass().getResourceAsStream("/com/sapi/apitest/datadriven/template/footer.tpl"));
    }

    /**
     * The polymophism used in the template design pattern.
     * @param bwriter
     * @param content
     */
    protected abstract void operation(BufferedWriter bwriter, String content);

    /**
     * Get the report.
     */
    public String getReport(BufferedWriter bwriter, String c) {
        beforeOperation(bwriter);

        operation(bwriter, c);

        afterOperation(bwriter);

        return content.toString();
    }
}