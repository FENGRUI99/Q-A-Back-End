package com.example.demo.mapper;

import com.example.demo.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<Question> listQuestion(@Param("id") String id);
    List<Question> search(@Param("target") String target);
    List<Question> listbyTag(@Param("tag") String tag);
    List<Question> answerSort();
    List<Question> timeSort();
    List<Question> likesSort();
    void likesAsync(@Param("id")String id,@Param("likes") String likes);
    List<Question> relevant(@Param("relevant") String relevant);
}
