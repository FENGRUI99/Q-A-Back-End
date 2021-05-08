package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;

public interface DeleteService {
    ResponseMessage deleteQuestion(String question_id);

    ResponseMessage deleteComment(Comment comment);
}
