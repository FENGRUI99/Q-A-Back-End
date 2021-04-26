package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface PublishMapper {
    void addPic(@Param("id") int id, @Param("list")List<String> files);
    List<Integer> getId( @Param("question")Question question);
}
