package com.example.demo.controller;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "User management API")
public class HelloController {

    @Resource
    RocketMQTemplate template;

    @Resource
    HelloService service;

    @ApiOperation(value = "add new user API",httpMethod = "POST")
    @RequestMapping("/register")
    public ResponseMessage register(@RequestBody studentInfo student){

        template.asyncSend("RegisterService", student, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        return ResponseMessage.success();
    }

    @ApiOperation(value = "user login API",httpMethod = "POST")
    @RequestMapping("/login")
    public ResponseMessage login(@RequestBody() studentInfo student){ return service.login(student.getUser_id(),student.getUser_psw()); }

    @ApiOperation(value = "check ID exits",httpMethod = "POST")
    @RequestMapping("/checkID")
    public ResponseMessage checkID(@RequestBody() studentInfo student){
        return service.checkID(student.getUser_id());
    }


}
