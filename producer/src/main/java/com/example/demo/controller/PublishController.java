package com.example.demo.controller;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.PublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "question and comment related API")
public class PublishController {

    @Resource
    PublishService service;

    @ApiOperation(value = "public question API",httpMethod = "POST")
    @RequestMapping("/publishQuestion")
    public ResponseMessage publishQuetion(@RequestBody() Question publish){ return service.publishQuestion(publish); }

    @ApiOperation(value = "public comment API",httpMethod = "POST")
    @RequestMapping("/publishComment")
    public ResponseMessage publishComment(@RequestBody() Comment comment){
        return service.publishComment(comment);
    }
}
