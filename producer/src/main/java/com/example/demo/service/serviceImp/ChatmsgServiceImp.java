package com.example.demo.service.serviceImp;

import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.ChatMsg;
import com.example.demo.service.service.ChatmsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatmsgServiceImp implements ChatmsgService {

    @Resource
    ChatMapper mapper;

    @Override
    public void insertChatmsg(ChatMsg msg) {
        mapper.insertMessage(msg.getUser_id(),msg.getSenduser_id(),msg.getText(),msg.getDate());
    }
}
