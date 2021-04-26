package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.FIleMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.PublishService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PublishServiceImp implements PublishService {

    @Resource
    StringRedisTemplate template;

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Resource
    FIleMapper mapper;

    @Override
    public ResponseMessage publishQuestion(Question question, List<String> files) {
        rocketMQTemplate.asyncSend("QuestionPublishService", question, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {


                try {
                    if(files.size()==0)
                        return;
                    Thread.sleep(2500);
                    List<Integer> list = mapper.getId(question);
                    int max=-1;

                    for (Integer item : list) {
                        System.out.println(item);
                        max=Math.max(item,max);
                    }
                    System.out.println(max);
                    template.opsForList().leftPush("pic_list",Integer.toString(max));
                    mapper.addPic(max, files);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("error");
            }
        });
        return ResponseMessage.success();
    }

    @Override
    public ResponseMessage publishQuestion(Question question) {
        rocketMQTemplate.asyncSend("QuestionPublishService", question, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

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
