package com.example.demo.controller;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Api(tags = "Userpage Information API")
public class UserController {

    @Resource
    UserService service;

    @RequestMapping("/UserInfo")
    public ResponseMessage<studentInfo> getUserInfo(@RequestBody Message message){
        return service.getUserInfo(message.getRequest());
    }
}
