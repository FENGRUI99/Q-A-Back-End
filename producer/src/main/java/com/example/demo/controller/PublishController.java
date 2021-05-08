package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.PublishService;
import io.lettuce.core.ScriptOutputType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "question and comment related API")
public class PublishController {

    @Resource
    PublishService service;

    @ApiOperation(value = "public question API",httpMethod = "POST")
    @RequestMapping("/publishQuestionWP")
    public ResponseMessage publishQuetion( @RequestParam("files") List<MultipartFile> files,
                                           @RequestParam("user_id") String user_id,@RequestParam("user_name") String user_name,
                                           @RequestParam("question_description") String description,@RequestParam("question_detail") String detail,
                                           @RequestParam("question_tags") String tags
                                           ) throws IOException {
        Question question=new Question();
        question.setQuestion_detail(detail);
        question.setQuestion_tags(tags);
        question.setQuestion_description(description);
        question.setUser_id(user_id);
        question.setUser_name(user_name);
        List<String> list=new ArrayList<>();
        if(files.size()>0) {
            for (MultipartFile file : files) {
                list.add(Base64Utils.encodeToString(file.getBytes()));
            }
        }
        return service.publishQuestion(question,list);
    }

    @ApiOperation(value = "public comment API",httpMethod = "POST")
    @RequestMapping("/publishComment")
    public ResponseMessage publishComment(@RequestBody() Comment comment){
        return service.publishComment(comment);
    }


    @ApiOperation(value = "public question API",httpMethod = "POST")
    @RequestMapping("/publishQuestion")
    public ResponseMessage publishQuetion(@RequestBody()Question question)  {
        return service.publishQuestion(question);
    }


}
