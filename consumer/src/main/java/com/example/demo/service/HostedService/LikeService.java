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
        try{
            String user_id=message.getRequest().split(" ")[0];
            String question_id=message.getRequest().split(" ")[1];
            template.opsForList().rightPush(user_id+"_likeList",question_id);
            int sum= Integer.parseInt(template.opsForHash().get("question_like",question_id).toString());
            sum+=Integer.parseInt(message.getMsg());
            template.opsForHash().put("question_like",question_id,String.valueOf(sum));
            mapper.likesAsync(Integer.parseInt(question_id),sum);
            questionPublishToEsService.likesAsync(question_id,sum);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
