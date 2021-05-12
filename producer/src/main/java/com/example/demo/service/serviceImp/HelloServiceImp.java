package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.HelloService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        try {
            System.out.println(1);
            Subject subject = SecurityUtils.getSubject();
            //封装登陆数据
            UsernamePasswordToken token = new UsernamePasswordToken(id,psw);
            token.setRememberMe(true);
            //登陆
            try {
                subject.login(token);
                ResponseMessage responseMessage=ResponseMessage.success(mapper.nameReturn(id));
                return responseMessage;
            }catch (UnknownAccountException e){
                e.printStackTrace();
                return ResponseMessage.fail("No such user");
            }catch (IncorrectCredentialsException e){
                e.printStackTrace();
                return ResponseMessage.fail("Wrong Password");
            }
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
            if(mapper.checkID(id)== null)
                return ResponseMessage.success();
            else
                return ResponseMessage.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public String PswByid(String id) {
        String psw = mapper.PswByid(id);
        return psw;
    }
}
