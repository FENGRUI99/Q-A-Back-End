package com.example.demo.service.QuestionService;

import com.example.demo.mapper.PublishMapper;
import com.example.demo.pojo.Comment;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@RocketMQMessageListener(topic = "CommentPublishService",consumerGroup = "CommentPublishGroup")
public class CommentpublishService implements RocketMQListener<Comment> {

    @Resource
    PublishMapper mapper;
    @Autowired
    QuestionPublishToEsServiceImpl questionPublishToEsService;
    @Resource
    StringRedisTemplate template;
    @Override
    public void onMessage(Comment comment) {

    }
}
