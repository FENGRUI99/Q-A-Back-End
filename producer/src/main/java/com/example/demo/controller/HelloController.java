package com.example.demo.controller;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Api(tags = "User management API")
public class HelloController {

    @Resource
    RocketMQTemplate RocketMQtemplate;

    @Resource
    HelloService service;

    @Resource
    StringRedisTemplate template;

    @ApiOperation(value = "add new user API",httpMethod = "POST")
    @RequestMapping("/register")
    public ResponseMessage register(@RequestBody studentInfo student, HttpServletRequest request){
        try {
            String ip = getIpAddr(request);
            if (template.opsForValue().get(student.getUser_mail() + ip.replace(".", "") + "Email_code") == null) {
                return ResponseMessage.fail("Code exprie, please try again!");
            }
            if (!student.getCode().equals(template.opsForValue().get(student.getUser_mail() + ip.replace(".", "") + "Email_code")))
                return ResponseMessage.fail("Email verification wrong!");
        }catch (Exception e){
            e.printStackTrace();
        }
        RocketMQtemplate.asyncSend("RegisterService", student, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        return ResponseMessage.success();
    }

    @ApiOperation(value = "user login API",httpMethod = "POST")
    @RequestMapping("/login")
    public ResponseMessage login(@RequestBody() studentInfo student){ return service.login(student.getUser_id(),student.getUser_psw()); }

    @ApiOperation(value = "check ID exits",httpMethod = "POST")
    @RequestMapping("/checkID")
    public ResponseMessage checkID(@RequestBody() studentInfo student){
        return service.checkID(student.getUser_id());
    }

    public  String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0
                    || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }


}
