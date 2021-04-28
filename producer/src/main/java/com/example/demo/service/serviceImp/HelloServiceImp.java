package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.HelloService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HelloServiceImp implements HelloService {

    @Resource
    HelloMapper mapper;

    @Resource
    StringRedisTemplate template;

    @Override
    public ResponseMessage login(String id, String psw) {
        System.out.println(id+" "+psw);
        try {
            if(mapper.login(id,psw).equals(psw)) {
                List<studentInfo> studentInfo = mapper.nameReturn(id);
                ResponseMessage responseMessage=ResponseMessage.success();
                responseMessage.setEntity(studentInfo);
                return responseMessage;
            }
            else
                return ResponseMessage.fail();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseMessage.fail();
        }

    }


    @Override
    public ResponseMessage checkID(String id) {
        try {
            Boolean flag=template.opsForSet().isMember("id_set",id);
            if(!flag)
                return ResponseMessage.success();
            if(flag!=null&&flag)
                return ResponseMessage.fail();
            if(mapper.checkID(id)==null)
                return ResponseMessage.success();
            else
                return ResponseMessage.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }


}
