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

/**
 * Factory class to construct the email entity.
 * 
 * @author wanhao01
 * 
 */
public class EmailSenderFactory {

    /**
     * the email sender.
     */
    private static SimpleEmailSender serviceSms = null;

    /**
     * Get SimpleEmailSender object.
     * 
     * @param type
     * @return
     */
    public static SimpleEmailSender getSender(EmailSenderType type) {
        if (type == EmailSenderType.SERVICE) {
            if (serviceSms == null) {
                serviceSms = new SimpleEmailSender("lbs_api@163.com",
                        "lbs_api_123456");
            }
            return serviceSms;
        }
        return null;
    }
}