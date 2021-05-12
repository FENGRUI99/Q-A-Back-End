package com.example.demo.mapper;

import com.example.demo.pojo.ChatMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatMapper {

    void insertMessage(@Param("user_id") String user_id,@Param("senduser_id") String senduser_id,
                       @Param("text")String text, @Param("text")String date);
}
