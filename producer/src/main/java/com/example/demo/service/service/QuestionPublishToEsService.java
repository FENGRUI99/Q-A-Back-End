package com.example.demo.service.service;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;

import java.text.ParseException;
import java.util.List;

/**
 *
 *
 */
public interface QuestionPublishToEsService {


    public boolean publishQuestion(Question question) ;

    public boolean publishComment(Comment comment);

}
