package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.ChatInfo;
import com.example.demo.pojo.ChatMsg;
import com.example.demo.pojo.chatRecords;
import com.example.demo.service.service.ChatmsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatmsgServiceImp implements ChatmsgService {

    @Resource
    ChatMapper mapper;

    @Resource
    StringRedisTemplate template;

    @Override
    public void insertChatmsg(ChatMsg msg) {
        mapper.insertMessage(msg.getUser_id(),msg.getSenduser_id(),msg.getName(),msg.getText(),msg.getDate());
    }

    @Override
    public ResponseMessage getChatMsg(String user_id, String senduser_id) {
       ArrayList<ChatInfo> list= mapper.getChatMsg(user_id,senduser_id);
       ResponseMessage message= ResponseMessage.success(new chatRecords(senduser_id,list));
       return message;
    }

    @Override
    public ResponseMessage getRecentChat(String request) {
        return null;
    }
}
