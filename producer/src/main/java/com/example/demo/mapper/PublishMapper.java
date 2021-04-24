package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PublishMapper {
    void publish(@Param("publish") Question publish);

    void comment(@Param("comment") Comment comment);
}
