package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.ChatMsg;

import java.util.List;

public interface ChatmsgService {

    public void insertChatmsg(ChatMsg chatmsg);
    ResponseMessage getChatMsg(String user_id, String senduser_id);
}
