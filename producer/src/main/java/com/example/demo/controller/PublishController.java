package com.example.demo.controller;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.PublishService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PublishController {

    @Resource
    PublishService service;

    @RequestMapping("/publishQuestion")
    public ResponseMessage publishQuetion(@RequestBody() Question publish){ return service.publishQuestion(publish); }

    @RequestMapping("/publishComment")
    public ResponseMessage publishComment(@RequestBody() Comment comment){
        return service.publishComment(comment);
    }
}
