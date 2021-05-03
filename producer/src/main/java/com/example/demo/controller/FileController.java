package com.example.demo.controller;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.FIleMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api("Picture uploud API")
@RestController
public class FileController {

    @Resource
    FIleMapper mapper;

    @Resource
    StringRedisTemplate template;

    @ApiOperation("Get image API")
    @RequestMapping("/img")
    public ResponseMessage<List<String>> getImg(@RequestBody Message message){
        ResponseMessage responseMessage=ResponseMessage.success();
        responseMessage.setEntity(mapper.getImage(message.getRequest()));
        return responseMessage;
    }


    @RequestMapping("/imglist")
    public ResponseMessage<List> getImglist(){
        List<String> list=template.opsForList().range("pic_list",0,-1);
        ResponseMessage responseMessage=ResponseMessage.success();
        responseMessage.setEntity(list);
        return responseMessage;
    }
}

