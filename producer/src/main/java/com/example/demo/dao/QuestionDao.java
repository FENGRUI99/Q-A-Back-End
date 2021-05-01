package com.example.demo.dao;


import com.example.demo.pojo.QuestionEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 功能描述：
 *
 * @Author： phm
 * @Date： 2021-04-29 20:16
 */
public interface QuestionDao extends ElasticsearchRepository<QuestionEs, String> {

}
