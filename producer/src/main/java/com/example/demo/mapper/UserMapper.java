package com.example.demo.mapper;

import com.example.demo.pojo.studentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    studentInfo getUserInfo(@Param("id") String id);
}
