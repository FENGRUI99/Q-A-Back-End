package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.service.service.EmailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerifyServiceImp implements EmailVerifyService {

    @Autowired
    JavaMailSender mailSender;

    @Override
    public ResponseMessage sendEmail(String user_mail) {
        String emailServiceCode = "1234";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("注册验证码");
        message.setText("Almost done! To complete your Louder sign up, we just need to verify your email address. Your verify code is  " +emailServiceCode+
                "Once verified, you can start using all of Louder's features to explore, ask, and answer questions here.：" );
        message.setFrom("fitzwang202006@163.com");
        message.setTo(user_mail);
        mailSender.send(message);
        return ResponseMessage.success();
    }
}
