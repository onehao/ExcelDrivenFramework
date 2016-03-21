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

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/***
 * The mail authentication.
 * 
 * @author wanhao01
 *
 */
public class EmailAuthenticator extends Authenticator {

    private String username;

    private String password;

    public EmailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;

    }

    String getPassword() {
        return password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}