package com.example.demo.service.QuestionService;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.PublishMapper;
import com.example.demo.pojo.Comment;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@RocketMQMessageListener(topic = "CommentPublishService_A",consumerGroup = "CommentPublishGroup")
public class CommentpublishService implements RocketMQListener<Comment> {

    @Resource
    PublishMapper mapper;
    @Autowired
    QuestionPublishToEsServiceImpl questionPublishToEsService;
    @Resource
    StringRedisTemplate template;
    @Override
    public void onMessage(Comment comment){
        Long commentId = template.boundValueOps("CommentId").increment(1);
        comment.setComment_id(commentId.intValue());
        mapper.publishComment(comment);
        mapper.commentIncrement(comment.getQuestion_id());
        questionPublishToEsService.publishComment(comment);
        template.opsForSet().add(comment.getUser_id() +"_CommentSet",comment.getQuestion_id());
    }
}