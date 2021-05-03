package com.example.demo.service.service;


import com.example.demo.configuration.ResponseMessage;

public interface QuestionService {
    ResponseMessage listQuestion(String id);
    ResponseMessage search(String target);
    ResponseMessage listbyTag(String tags);
    ResponseMessage answerSort(String flag);
    ResponseMessage timeSort(String flag);
    ResponseMessage likesSort(String flag);
    ResponseMessage relevant(String question_tags);

}
