package com.example.demo.service.service;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface PublishService {

    ResponseMessage publishQuestion(Question question, List<String> files);
    ResponseMessage publishQuestion(Question question) ;
    ResponseMessage publishComment(Comment comment);

}
