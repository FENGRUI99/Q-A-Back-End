package com.example.demo.controller;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@RestController
@Api(tags = "Userpage Information API")
public class UserController {

    @Resource
    UserService service;

    @RequestMapping("/UserInfo")
    public ResponseMessage<studentInfo> getUserInfo(@RequestBody Message message){
        return service.getUserInfo(message.getRequest());
    }

    @RequestMapping("/Upload")
    public  ResponseMessage uploadImg(@RequestParam("file") List<MultipartFile> files, @RequestParam("user_id") String user_id) throws IOException {
        return service.uploadImg(files.get(0),user_id);
    }

    @RequestMapping("/getPhoto")
    public ResponseMessage<String> getPhoto(@RequestBody Message message){
        return service.getPhoto(message.getRequest());
    }

    @RequestMapping("/userIDQuestion")
    public ResponseMessage<List> getQuestionsById(@RequestBody Message message){
        return service.getQuestionsById(message.getRequest());
    }

    @RequestMapping("/userLikeQuestion")
    public ResponseMessage<List> getQuestionsByLike(@RequestBody Message message){
        return service.getQuestionsByLike(message.getRequest());
    }

    @RequestMapping("/userCommentQuestion")
    public ResponseMessage<List> getQuestionByComment(@RequestBody Message message){
        return service.getQuestionByComment(message.getRequest());
    }

    @RequestMapping("/sendEmailForChangPsw")
    public ResponseMessage sendMail(@RequestBody Message message){
        return service.sendMail(message.getRequest(),message.getMsg());
    }

    @RequestMapping("/changePsw")
    public ResponseMessage changePsw(@RequestBody Message message){
        return service.changePsw(message.getRequest(),message.getMsg());
    }

    @RequestMapping("/changeInfo")
    public ResponseMessage changeInfo(@RequestBody UserInfo userInfo){
        return service.changeInfo(userInfo);
    }

}
