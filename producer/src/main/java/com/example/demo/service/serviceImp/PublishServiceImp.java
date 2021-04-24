package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.PublishMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.PublishService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PublishServiceImp implements PublishService {

    @Resource
    StringRedisTemplate template;

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Resource
    PublishMapper mapper;

    @Override
    public ResponseMessage publishQuestion(Question question) {
        rocketMQTemplate.asyncSend("QuestionPublishService", question, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("error");
            }
        });
        return ResponseMessage.success();
    }

    @Override
    public ResponseMessage publishComment(Comment comment) {
        rocketMQTemplate.asyncSend("CommentPublishService", comment, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("error");
            }
        });
        return ResponseMessage.success();
    }


}
