package com.example.demo.service.QuestionService;

import com.example.demo.configuration.ElasticsearchConfig;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.PublishMapper;
import com.example.demo.pojo.Question;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
@RocketMQMessageListener(topic = "QuestionPublishService",consumerGroup = "QuestionPublishGroup")
public class QuestionPublishService implements RocketMQListener<Question> {

    @Resource
    StringRedisTemplate template;

    @Resource
    RestHighLevelClient client;

    @Resource
    PublishMapper mapper;

    @Override
    public void onMessage(Question question) {
            int length=template.opsForHash().entries("question_like").size();
            template.opsForHash().put("question_like", String.valueOf(length+1),"0");
            template.opsForZSet().incrementScore("question_contribute",question.getUser_id(),1);
            mapper.publishQuestion(question);
            String[] tags=question.getQuestion_tags().split(",");
            for (String tag : tags) {
                template.opsForZSet().incrementScore("question_tags",tag,1);
            }

            boolean flag=template.opsForHash().hasKey("question_like", String.valueOf(question.getQuestion_id()));
            if(flag)
                template.opsForSet().remove("question_id", String.valueOf(question.getQuestion_id()));


    }
}
