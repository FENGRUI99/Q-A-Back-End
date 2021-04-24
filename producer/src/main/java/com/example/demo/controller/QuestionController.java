package com.example.demo.controller;



import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.QuestionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class QuestionController {

    @Resource
    QuestionService service;

    @RequestMapping("/listQuestion")
    public ResponseMessage<Question> listQuestion(@RequestBody Message message) {
        //  只要一个 request
        return service.listQuestion(message.getRequest());
    }

    @RequestMapping("/search")
    public ResponseMessage<Question> search(@RequestBody Message message){
        //  只要一个 request
        // 传 关键字
        // 一个
        return service.search(message.getRequest());
    }

    @RequestMapping("/listbyTag")
    public ResponseMessage<Question> listbyTag(@RequestBody Message message){
        //  只要一个 request,返回一个tag或多个
        //  tag拼接的字符串
        //  eg. 选择html css 返回1,2
        //  选择html 返回1
        return service.listbyTag(message.getRequest());
    }

    @RequestMapping("/sortbyTime")
    public ResponseMessage<Question> sortbyTime(){
        return service.timeSort();
    }

    @RequestMapping("/sortbyLikes")
    public ResponseMessage<Question> sortbyLikes(){
        return service.likesSort();
    }

    @RequestMapping("/sortbyAnswers")
    public ResponseMessage<Question> sortbyAnswers(){
        return service.answerSort();
    }

    @RequestMapping("/relevant")
    public ResponseMessage<Question> relevant(@RequestBody Message message){
        return service.relevant(message.getRequest());
    }

}
