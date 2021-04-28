package com.example.demo.controller;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.EmailVerifyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EmailVerifyController {

    @Resource
    EmailVerifyService service;

    @RequestMapping("/sendEmail")
    public ResponseMessage sendEmail(@RequestBody Message message){
        System.out.println(message.getRequest());
        return service.sendEmail(message.getRequest());
    }
}
