package com.example.demo.controller;
import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.service.service.HotestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "heat user and question API")
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

    @ApiOperation(value = "liked API",httpMethod = "POST")
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
//        template.asyncSend("LikeService_topic", message, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//
//            }
//        });
        return service.like(message);
    }

    @ApiOperation(value = "hot user API",httpMethod = "GET")
    @RequestMapping("/contributor")
    public ResponseMessage contributor(){
        return service.contributor();
    }

    @ApiOperation(value = "hot tags API",httpMethod = "GET")
    @RequestMapping("/hotTags")
    public ResponseMessage hotTags(){
        return service.hotTags();
    }

    @ApiOperation(value = "user like list API",httpMethod = "GET")
    @RequestMapping("/userLike")
    public ResponseMessage userLike(@RequestBody Message message){
        /*
        给user_id
        * */
        return service.userLike(message.getRequest());
    }
}
