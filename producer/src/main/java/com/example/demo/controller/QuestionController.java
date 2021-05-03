package com.example.demo.controller;



import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "HomePage related API")
public class QuestionController {

    @Resource
    QuestionService service;

    @ApiOperation(value = "list questions API",httpMethod = "GET")
    @RequestMapping("/listQuestion")
    public ResponseMessage<Question> listQuestion(@RequestBody Message message) {
        //  只要一个 request
        return service.listQuestion(message.getRequest());
    }

    @ApiOperation(value = "search API",httpMethod = "GET")
    @RequestMapping("/search")
    public ResponseMessage<Question> search(@RequestBody Message message){
        //  只要一个 request
        // 传 关键字
        // 一个
        return service.search(message.getRequest());
    }

    @ApiOperation(value = "list by tags API",httpMethod = "GET")
    @RequestMapping("/listbyTags")
    public ResponseMessage<Question> listbyTag(@RequestBody Message message){
        //  只要一个 request,返回一个tag或多个
        //  tag拼接的字符串
        //  eg. 选择html css 返回1,2
        //  选择html 返回1
        return service.listbyTag(message.getRequest());
    }

    @ApiOperation(value = "questions sort by time API",httpMethod = "GET")
    @RequestMapping("/sortbyTime")
    public ResponseMessage<Question> sortbyTime(){
        return service.timeSort();
    }

    @ApiOperation(value = "question sort by likes API",httpMethod = "GET")
    @RequestMapping("/sortbyLikes")
    public ResponseMessage<Question> sortbyLikes(){
        return service.likesSort();
    }

    @ApiOperation(value = "question sort by likes API",httpMethod = "GET")
    @RequestMapping("/sortbyAnswers")
    public ResponseMessage<Question> sortbyAnswers(){
        return service.answerSort();
    }

    @ApiOperation(value = "question relevant API",httpMethod = "GET")
    @RequestMapping("/relevant")
    public ResponseMessage<Question> relevant(@RequestBody Message message){
        return service.relevant(message.getRequest());
    }

}
