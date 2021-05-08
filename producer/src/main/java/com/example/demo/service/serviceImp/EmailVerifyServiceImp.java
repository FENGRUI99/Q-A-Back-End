package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.EmailMapper;
import com.example.demo.service.service.EmailVerifyService;
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

    @Value("${spring.mail.username}")
    String user;

    @Resource
    StringRedisTemplate template;

    @Override
    public ResponseMessage sendEmail(String user_mail,String ip) {
        try {
            if(user_mail==null)
                return ResponseMessage.fail("email null");
            String emailServiceCode = UUID.randomUUID().toString().replace("-","").toLowerCase().substring(0,6);
            template.opsForValue().set(user_mail+ip.replace(".","")+"Email_code",emailServiceCode,60,TimeUnit.SECONDS);
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject("Greeting from Louder");
            helper.setText(
                    "Verification Code: "+ emailServiceCode +  "   \n"+"  Almost done! To complete your Louder sign up, we just need to verify your email address. Your verify code is  "  +
                    " Once verified, you can start using all of Louder's features to explore, ask, and answer questions here.：");
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
