package com.example.demo.service.serviceImp;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.service.HotestService;
import com.example.demo.service.service.QuestionPublishToEsService;
import com.example.demo.service.service.UpdateToEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class HotestServiceImp implements HotestService {

    @Resource
    StringRedisTemplate template;

    @Resource
    QuestionMapper mapper;

    @Autowired
    UpdateToEsService UpdateToLikeEsService;

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
            int tag=100;
            while(template.opsForList().size("lock")>0){
                tag--;
                if(tag==0)
                    template.expire("lock",1000,TimeUnit.SECONDS);
            }
            if(template.opsForSet().size(user_id+"_likeSet")>0){
                Set<String> list=template.opsForSet().members(user_id+"_likeSet");
                ResponseMessage message=ResponseMessage.success();
                message.setEntity(list);
                for (String s : list) {
                    System.out.println(s);
                }
                return message;
            }else{
               template.opsForSet().add(user_id+"_likeSet","-1");
            }
            return ResponseMessage.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public ResponseMessage like(Message message) {
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

            UpdateToLikeEsService.UpdateToLikeEsService(question_id,sum);
            mapper.likesAsync(question_id,String.valueOf(sum));
            if(template.opsForValue().get("Mylock").equals(uuid)) {
                System.out.println(1);
                template.expire("Mylock", 0, TimeUnit.NANOSECONDS);
            }
            template.opsForList().rightPop("lock");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.success();
    }

}
