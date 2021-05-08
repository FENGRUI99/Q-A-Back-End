package com.example.demo.controller;

import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.service.service.DeleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("Delete API")
@RestController
public class DeleteController {

    @Resource
    DeleteService deleteService;

    @ApiOperation(value = "delete question API",httpMethod = "POST")
    @RequestMapping("/deleteQuestion")
    public ResponseMessage deleteQuestion(@RequestBody Message message){
        return deleteService.deleteQuestion(message.getRequest());
    }

    @ApiOperation(value = "delete comment API",httpMethod = "POST")
    @RequestMapping("/deleteComment")
    public ResponseMessage deleteComment(@RequestBody Comment comment){
        return deleteService.deleteComment(comment);
    }
}
