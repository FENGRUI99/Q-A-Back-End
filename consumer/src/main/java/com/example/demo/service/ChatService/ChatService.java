package com.example.demo.service.ChatService;


import com.example.demo.mapper.ChatMapper;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.pojo.ChatMsg;
import com.example.demo.pojo.studentInfo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@RocketMQMessageListener(topic = "ChatService",consumerGroup = "ChatConsumegroup")
@Service
public class ChatService implements RocketMQListener<ChatMsg> {

    @Resource
    ChatMapper mapper;

    @Override
    public void onMessage(ChatMsg msg) {
        mapper.insertMessage(msg.getUser_id(),msg.getSenduser_id(),msg.getText(),msg.getDate());
    }
}
