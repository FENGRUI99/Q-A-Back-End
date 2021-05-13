package com.example.demo.controller;

import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.service.service.ChatmsgService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ChatController {

    @Resource
    ChatmsgService service;

    @RequestMapping("/chatRecords")
    public ResponseMessage getChatRecords(@RequestBody Message message){
        return service.getChatMsg(message.getRequest(), message.getMsg());
    }

    @RequestMapping("/getRecentChat")
    protected ResponseMessage getRecentChat(@RequestBody Message message){
        return service.getRecentChat(message.getRequest());
    }
}
