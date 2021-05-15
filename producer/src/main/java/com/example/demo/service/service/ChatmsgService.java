package com.example.demo.service.service;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.ChatMsg;

public interface ChatmsgService {

    public void insertChatmsg(ChatMsg chatmsg);
    ResponseMessage getChatMsg(String user_id, String senduser_id);
    ResponseMessage getRecentChat(String request);
    void setHotestChat(String user_id, String senduser_id, boolean b);
}
