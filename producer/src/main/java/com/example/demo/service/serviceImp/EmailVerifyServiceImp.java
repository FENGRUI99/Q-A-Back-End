package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.EmailMapper;
import com.example.demo.service.service.EmailVerifyService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerifyServiceImp implements EmailVerifyService {

    @Resource
    JavaMailSender mailSender;

    @Resource
    EmailMapper mapper;

    @Value("${spring.mail.username}")
    String user;

    @Resource
    StringRedisTemplate template;

    @Override
    public ResponseMessage sendEmail(String user_id) {

        try {
            System.out.println(user_id);
            String user_mail=mapper.getMail(user_id);
            System.out.println(user_mail);
            if(user_mail==null)
                return ResponseMessage.fail();
            String emailServiceCode = UUID.randomUUID().toString().replace("-","").toLowerCase().substring(0,6);
            template.opsForHash().put(user_id+"Email_code",user_mail,emailServiceCode);
            template.expire(user_id+"Email_code",15*60, TimeUnit.SECONDS);
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("注册验证码");
            helper.setText("Almost done! To complete your Louder sign up, we just need to verify your email address. Your verify code is  " + emailServiceCode +
                    "Once verified, you can start using all of Louder's features to explore, ask, and answer questions here.：");
            helper.setFrom(user);
            helper.setTo(user_mail);
            mailSender.send(message);
            return ResponseMessage.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseMessage.fail();
        }
    }
}
