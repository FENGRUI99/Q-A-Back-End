package com.example.demo.service.service;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;

public interface HotestService {
    ResponseMessage contributor();
    ResponseMessage hotTags();
    ResponseMessage userLike(String user_id);
    ResponseMessage like(Message message);
}
