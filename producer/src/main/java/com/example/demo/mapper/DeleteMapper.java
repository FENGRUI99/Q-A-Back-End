package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeleteMapper {
    void deleteQuestion(@Param("question_id") String question_id);

    void deleteComment(@Param("comment_id") int comment_id);
}
