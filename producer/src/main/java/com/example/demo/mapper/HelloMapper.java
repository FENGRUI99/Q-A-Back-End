package com.example.demo.mapper;

import com.example.demo.pojo.studentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface HelloMapper {
    String login(@Param("id") String id, @Param("psw") String psw);
    String checkID(@Param("id")String id);
    List<studentInfo> nameReturn(String id);

    public String PswByid(String id);
}
