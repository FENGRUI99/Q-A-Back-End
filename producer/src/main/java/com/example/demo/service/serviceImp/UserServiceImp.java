package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper mapper;

    @Override
    public ResponseMessage getUserInfo(String id) {
        studentInfo student= mapper.getUserInfo(id);
        return ResponseMessage.success(student);
    }
}
