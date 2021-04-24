package com.example.demo.service.QuestionService;

import com.example.demo.mapper.PublishMapper;
import com.example.demo.pojo.Comment;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(topic = "CommentPublishService",consumerGroup = "CommentPublishGroup")
public class CommentpublishService implements RocketMQListener<Comment> {

    @Resource
    PublishMapper mapper;

    @Override
    public void onMessage(Comment comment) {
        try{
            mapper.publishComment(comment);
            mapper.commentIncrement(comment.getQuestion_id());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
