package com.example.demo.service.serviceImp;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.service.HotestService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class HotestServiceImp implements HotestService {

    @Resource
    StringRedisTemplate template;

    @Resource
    QuestionMapper mapper;




    @Override
    public ResponseMessage contributor() {

        try {
            Set<String> set=  template.opsForZSet().reverseRange("question_contribute",0,9);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(set);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public ResponseMessage hotTags() {

        try {
            Set<String> set=template.opsForZSet().reverseRange("question_tags",0,9);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(set);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public ResponseMessage userLike(String user_id) {
        try {
            if(template.opsForList().size(user_id+"_likeList")>0){
                List<String> list=template.opsForList().range(user_id+"_likeList",0,-1);
                ResponseMessage message=ResponseMessage.success();
                message.setEntity(list);
                return message;
            }else{
               template.opsForList().rightPush(user_id+"_likeList","");
            }
            return ResponseMessage.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

}
