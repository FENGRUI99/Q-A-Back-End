package com.example.demo.mapper;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FIleMapper {
    void addPic(@Param("id") String id, @Param("list")List<String> files);
    List<String> getImage(@Param("id")String id);
    void publishQuestion(@Param("question") Question publish,@Param("id") String id);
    void publishComment(@Param("comment") Comment comment);
    void commentIncrement(@Param("id") String question_id);
}
