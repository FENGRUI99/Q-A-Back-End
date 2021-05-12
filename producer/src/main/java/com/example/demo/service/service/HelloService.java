package com.example.demo.service.service;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.studentInfo;

public interface HelloService {
    ResponseMessage login(String id, String psw);
    ResponseMessage checkID(String id);

    public String PswByid(String id);
}
