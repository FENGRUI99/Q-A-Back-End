package com.example.demo.mapper;

import com.example.demo.pojo.ChatBoxMessage;
import com.example.demo.pojo.ChatInfo;
import com.example.demo.pojo.ChatMsg;
import com.example.demo.pojo.chatRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ChatMapper {

    void insertMessage(@Param("user_id") String user_id,@Param("senduser_id") String senduser_id,
                       @Param("name") String name,@Param("text")String text, @Param("date")String date);
    ArrayList<ChatInfo> getChatMsg(@Param("user_id") String user_id, @Param("senduser_id") String senduser_id);

    List<ChatBoxMessage> getRecentChat(@Param("list") List<String> list,@Param("id") String id);
}
