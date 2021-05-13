package com.example.demo.mapper;

import com.example.demo.pojo.ChatInfo;
import com.example.demo.pojo.ChatMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ChatMapper {

    void insertMessage(@Param("user_id") String user_id,@Param("senduser_id") String senduser_id,
                       @Param("name") String name,@Param("text")String text, @Param("text")String date);

    ArrayList<ChatInfo> getChatMsg(@Param("user_id") String user_id, @Param("senduser_id") String senduser_id);
}
