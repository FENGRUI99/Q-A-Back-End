package com.example.demo.mapper;

import com.example.demo.pojo.studentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface UserMapper {
    studentInfo getUserInfo(@Param("id") String id);
    void uploadImg(@Param(("file")) String file, @Param("id") String user_id);
    String getPhoto(@Param("id") String request);

}
