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
package com.sapi.apitest.datadriven.notificaitons.email;

import javax.mail.Multipart;

/**
 * Define the email entity.
 * @author wanhao01
 *
 */
public class EmailEntity {

    private String subject;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content
     */
    public Multipart getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(Multipart content) {
        this.content = content;
    }

    private Multipart content;
}