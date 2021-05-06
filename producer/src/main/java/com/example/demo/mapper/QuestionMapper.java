package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    void likesAsync(@Param("id")String id,@Param("likes") String likes);
}
