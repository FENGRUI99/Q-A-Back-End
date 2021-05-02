package com.example.demo.service.serviceImp;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.dao.QuestionDao;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.QuestionEs;
import com.example.demo.service.service.QuestionService;
import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.naming.directory.SearchControls;
import javax.security.auth.kerberos.KerberosTicket;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionServiceImp implements QuestionService {

    @Resource
    QuestionMapper mapper;

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Resource
    QuestionDao dao;

    @Override
    public ResponseMessage listQuestion(String id) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment");
            QueryBuilder builder=QueryBuilders.matchAllQuery();

            searchSourceBuilder.query(builder).sort(fsb);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to get the questions");
        }

    }

    @Override
    public ResponseMessage search(String target) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //实现高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("question_description");
            highlightBuilder.field("question_detail");
            highlightBuilder.requireFieldMatch(true);  //多个单词高亮的话，要把这个设置为trues
            highlightBuilder.preTags("<span style=\"color: #A5D6A7\">");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
            //匹配目标


            String[] text=new String[1];
            text[0]=target;
            String[] fileds={"question_tags"};


            WildcardQueryBuilder wildcardQueryBuilder= QueryBuilders.wildcardQuery("question_description","*"+target+"*");
                    //QueryBuilders.moreLikeThisQuery(fileds,text,null);

            searchSourceBuilder.query(wildcardQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            //打包
            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                Map<String, HighlightField> highlight =documentFields.getHighlightFields();
                HighlightField title=highlight.get("question_description");
                HighlightField detail=highlight.get("question_detail");
                Map<String, Object> source=documentFields.getSourceAsMap();
                if(title!=null){
                    Text[] fragments=title.fragments();
                    String newTitle="";
                    for (Text fragment : fragments) {
                        newTitle+=fragment;
                    }
                    source.put("question_description",newTitle);
                }
                if(detail!=null){
                    Text[] fragments=detail.fragments();
                    String newTitle="";
                    for (Text fragment : fragments) {
                        newTitle+=fragment;
                    }
                    source.put("question_detail",newTitle);
                }
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to search");
        }

    }

    @Override
    public ResponseMessage listbyTag(String tags) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.ASC);

            MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("question_tags",tags);
            searchSourceBuilder.query(termQueryBuilder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            Map<String,Map<String,Object>> map=new HashMap<>();
            ArrayList<Map<String,Object>> list = new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to search");
        }
    }

    @Override
    public ResponseMessage answerSort() {
        try {

            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.DESC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();

            searchSourceBuilder.query(builder).sort(fsb);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to answerSort");
        }
    }

    @Override
    public ResponseMessage timeSort() {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("create_time").order(SortOrder.DESC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();

            searchSourceBuilder.query(builder).sort(fsb);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            ArrayList<Map<String,Object>> list = new ArrayList<>();
            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to timeSort");
        }
    }

    @Override
    public ResponseMessage likesSort() {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("likes").order(SortOrder.DESC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();

            searchSourceBuilder.query(builder).sort(fsb);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to likesSort");
        }
    }

    @Override
    public ResponseMessage relevant(String question_tags) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.ASC);

            MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("question_tags",question_tags);
            searchSourceBuilder.query(termQueryBuilder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            Map<String,Map<String,Object>> map=new HashMap<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                map.put(documentFields.getId(),documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(map);
            return responseMessage;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseMessage.fail(question_tags);
        }
    }






}
