package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PublishMapper {
    void publishQuestion(@Param("question") Question publish,@Param("id") String id);
    void publishComment(@Param("comment") Comment comment);
    void commentIncrement(@Param("id") String question_id);
}
