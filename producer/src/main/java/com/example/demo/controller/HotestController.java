package com.example.demo.controller;
import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.service.service.HotestService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HotestController {
    /*
    * 写发表问题的时候要在redis中加tag跟user的热度
    * 不要忘了
    * 两个都是zset
    * */

    @Resource
    HotestService service;

    @Resource
    RocketMQTemplate template;

    @RequestMapping("/like")
    public ResponseMessage like(@RequestBody Message message){
        /*
        * 点赞传两个参数
        * request给 user_id和question_id
        * eg. "request":"1 3"
        * user_id为1，question_id为3
        * 点赞msg给1
        * 取消给-1
        * */
        template.asyncSend("LikeService_topic", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        return ResponseMessage.success();
    }

    @RequestMapping("/contributor")
    public ResponseMessage contributor(){
        return service.contributor();
    }

    @RequestMapping("/hotTags")
    public ResponseMessage hotTags(){
        return service.hotTags();
    }

    @RequestMapping("/userLike")
    public ResponseMessage userLike(@RequestBody Message message){
        /*
        给user_id
        * */
        return service.userLike(message.getRequest());
    }
}
