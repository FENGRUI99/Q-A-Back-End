package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;

import javax.servlet.http.HttpServletRequest;

public interface EmailVerifyService {
    ResponseMessage sendEmail(String user_mail, String ip);
}
