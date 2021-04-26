package com.example.demo.controller;


import com.example.demo.configuration.Message;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.FIleMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    @Resource
    FIleMapper mapper;

    @Resource
    StringRedisTemplate template;

    @RequestMapping("/img")
    public ResponseMessage<String> getImg(@RequestBody Message message){
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
