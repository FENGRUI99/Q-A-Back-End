package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.studentInfo;
import com.example.demo.service.service.UserService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserMapper mapper;

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Resource
    StringRedisTemplate template;

    @Override
    public ResponseMessage getUserInfo(String id) {
        studentInfo student= mapper.getUserInfo(id);
        return ResponseMessage.success(student);
    }

    @Override
    public ResponseMessage uploadImg(MultipartFile file, String user_id) throws IOException {
        String pic= Base64Utils.encodeToString(file.getBytes());
        mapper.uploadImg(pic,user_id);
        return ResponseMessage.success();
    }

    @Override
    public ResponseMessage<String> getPhoto(String request) {
        ResponseMessage message=ResponseMessage.success();
        message.setEntity(mapper.getPhoto(request));
        return message;
    }

    @Override
    public ResponseMessage<List> getQuestionsById(String request) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("user_id", request);
            searchSourceBuilder.query(termQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage message = ResponseMessage.success();
            message.setEntity(list);
            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public ResponseMessage<List> getQuestionsByLike(String user_id) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            LinkedHashSet<String> id_list= (LinkedHashSet) template.opsForSet().members(user_id + "_likeSet");
            BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
            boolean flag=false;
            for (String id : id_list) {
                if(!id.equals("-1")){
                    flag=true;
                    TermQueryBuilder termQueryBuilder=QueryBuilders.termQuery("question_id",id);
                    boolQueryBuilder.should(termQueryBuilder);
                }
            }
            if(!flag)
                return ResponseMessage.success();
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage message = ResponseMessage.success();
            message.setEntity(list);
            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }

    @Override
    public ResponseMessage<List> getQuestionByComment(String msg) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            BoolQueryBuilder queryBuilder=QueryBuilders.boolQuery();
            LinkedHashSet<String> linkedHashSet= (LinkedHashSet<String>) template.opsForSet().members(msg+"_CommentSet");
            if(linkedHashSet.size()<1)
                return ResponseMessage.success();
            for (String s : linkedHashSet) {
                queryBuilder.should(QueryBuilders.termQuery("question_id",s));
            }
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> list = new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage message = ResponseMessage.success();
            message.setEntity(list);
            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.fail();
    }
}
