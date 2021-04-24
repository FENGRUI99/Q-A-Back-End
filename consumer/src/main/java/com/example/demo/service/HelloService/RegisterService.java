package com.example.demo.service.HelloService;

import com.example.demo.mapper.HelloMapper;
import com.example.demo.pojo.studentInfo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@RocketMQMessageListener(topic = "RegisterService",consumerGroup = "registerConsume")
@Service
public class RegisterService implements RocketMQListener<studentInfo> {

    @Resource
    StringRedisTemplate template;

    @Resource
    HelloMapper mapper;

    @Override
    public void onMessage(studentInfo student) {
        template.opsForSet().add("id_set",student.getUser_id());
        template.opsForZSet().add("question_contribute",student.getUser_name(),0);
        mapper.register(student);
    }
}
