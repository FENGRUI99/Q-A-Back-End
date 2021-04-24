package com.example.demo.service.service;


import com.example.demo.configuration.ResponseMessage;

public interface QuestionService {
    ResponseMessage listQuestion(String id);
    ResponseMessage search(String target);
    ResponseMessage listbyTag(String tags);
    ResponseMessage answerSort();
    ResponseMessage timeSort();
    ResponseMessage likesSort();
    ResponseMessage relevant(String question_tags);
}
