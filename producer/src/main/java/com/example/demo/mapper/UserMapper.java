package com.example.demo.mapper;

import com.example.demo.pojo.UserInfo;
import com.example.demo.pojo.studentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface UserMapper {
    UserInfo getUserInfo(@Param("id") String id);
    void uploadImg(@Param(("file")) String file, @Param("id") String user_id);
    String getPhoto(@Param("id") String request);
    String checkEmailAndID(@Param("id") String id);
    void changePsw(@Param("id") String id, @Param("psw") String newPsw);
    String checkPsw(@Param("id") String id);
}
