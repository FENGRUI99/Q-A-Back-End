package com.example.demo.service.QuestionService;

import com.example.demo.pojo.Question;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "QuestionPublishService",consumerGroup = "QuestionPublishGroup")
public class QuestionpublishService implements RocketMQListener<Question> {
    @Override
    public void onMessage(Question question) {

    }
}
