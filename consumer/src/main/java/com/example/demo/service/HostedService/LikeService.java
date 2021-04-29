package com.example.demo.service.HostedService;

import com.example.demo.configuration.Message;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.mapper.QuestionMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
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

    @Override
    public void onMessage(Message message) {
        String uuid=UUID.randomUUID().toString();
        try{
            template.opsForList().leftPush("lock","1");
            template.opsForValue().setIfAbsent("Mylock",uuid);
            String user_id=message.getRequest().split(" ")[0];
            String question_id=message.getRequest().split(" ")[1];
            int sum= Integer.parseInt(template.opsForHash().get("question_like",question_id).toString());
            if(!template.opsForSet().isMember(user_id + "_likeSet",question_id)) {
                template.opsForSet().add(user_id + "_likeSet",question_id);
                sum++;
            } else {
                template.opsForSet().remove(user_id + "_likeSet",question_id);
                sum--;
            }
            template.opsForHash().put("question_like",question_id,String.valueOf(sum));
            mapper.likesAsync(question_id,String.valueOf(sum));
            if(template.opsForValue().get("Mylock")==uuid) {
                System.out.println(1);
                template.expire("Mylock", 0, TimeUnit.NANOSECONDS);
            }
            template.opsForList().rightPop("lock");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
