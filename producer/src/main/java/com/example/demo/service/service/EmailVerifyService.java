package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;

public interface EmailVerifyService {
    ResponseMessage sendEmail(String user_mail);
}
