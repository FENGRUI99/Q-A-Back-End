package com.example.demo.controller;

import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.service.service.ChatmsgService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
public class ChatController {

    @Resource
    ChatmsgService service;

    @Resource
    StringRedisTemplate template;

    @RequestMapping("/chatRecords")
    public ResponseMessage getChatRecords(@RequestBody Message message){
        return service.getChatMsg(message.getRequest(), message.getMsg());
    }

    @RequestMapping("/getRecentChat")
    public ResponseMessage getRecentChat(@RequestBody Message message){
        return service.getRecentChat(message.getRequest());
    }


}
