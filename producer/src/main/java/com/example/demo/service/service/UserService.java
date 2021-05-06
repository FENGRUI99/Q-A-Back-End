package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    ResponseMessage getUserInfo(String id);
    ResponseMessage uploadImg(MultipartFile file, String user_id) throws IOException;
    ResponseMessage<String> getPhoto(String request);
    ResponseMessage<List> getQuestionsById(String request);
    ResponseMessage<List> getQuestionsByLike(String request);
    ResponseMessage<List> getQuestionByComment(String msg);
}
