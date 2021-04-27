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
import java.util.Date;
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
        try {
            Date date=new Date();
            String id=String.valueOf(date.getTime());
            template.opsForHash().put("question_like", String.valueOf(id), "0");
            template.opsForZSet().incrementScore("question_contribute", question.getUser_id(), 1);
            mapper.publishQuestion(question,id);
            String[] tags = question.getQuestion_tags().split(",");
            for (String tag : tags) {
                template.opsForZSet().incrementScore("question_tags", tag, 1);
            }

        }catch (Exception e) {
          e.printStackTrace();
        }

    }
}
