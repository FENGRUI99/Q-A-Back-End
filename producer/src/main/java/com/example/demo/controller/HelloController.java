package com.example.demo.controller;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.HelloService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    RocketMQTemplate template;

    @Resource
    HelloService service;

    @RequestMapping("/register")
    public ResponseMessage send(@RequestBody studentInfo student){

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

    @RequestMapping("/login")
    public ResponseMessage login(@RequestBody() studentInfo student){ return service.login(student.getUser_id(),student.getUser_psw()); }

    @RequestMapping("/checkID")
    public ResponseMessage checkID(@RequestBody() studentInfo student){
        return service.checkID(student.getUser_id());
    }


}
