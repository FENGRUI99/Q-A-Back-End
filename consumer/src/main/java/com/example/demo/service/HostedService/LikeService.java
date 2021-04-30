package com.example.demo.service.HostedService;

import com.example.demo.configuration.Message;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.QuestionService.QuestionPublishToEsService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RocketMQMessageListener(topic = "LikeService_topic", consumerGroup = "likeConsume")
@Service
public class LikeService implements RocketMQListener<Message> {

    @Resource
    StringRedisTemplate template;

    @Resource
    QuestionMapper mapper;
    @Autowired
    QuestionPublishToEsService questionPublishToEsService;
    @Override
    public void onMessage(Message message) {

    }
}
