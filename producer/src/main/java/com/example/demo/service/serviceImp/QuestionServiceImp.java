package com.example.demo.service.serviceImp;
import com.example.demo.configuration.ResponseMessage;
import com.example.demo.dao.QuestionDao;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.service.service.QuestionService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
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
    public ResponseMessage listQuestion(String user_tags) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder builder=QueryBuilders.matchAllQuery();
            searchSourceBuilder.size(2000);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            ArrayList<Map<String,Object>> list=new ArrayList<>();

            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            Collections.shuffle(list);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to get the questions");
        }

    }

    @Override
    public ResponseMessage search(String target) {

        try {
            target=target.toLowerCase();
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            //实现高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("question_description");
            highlightBuilder.field("question_detail");
            highlightBuilder.requireFieldMatch(true);  //多个单词高亮的话，要把这个设置为trues
            highlightBuilder.preTags("<span style=\"color:#B20610\">");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
            //匹配目标
            BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
            String[] text=target.split(" ");
            if(text.length>1){
                for (String s : text) {
                    WildcardQueryBuilder wildcardQueryBuilder= QueryBuilders.wildcardQuery("question_description","*"+s+"*");
                    WildcardQueryBuilder wildcardQueryBuilder1= QueryBuilders.wildcardQuery("question_detail","*"+s+"*");
                    boolQueryBuilder.should(wildcardQueryBuilder);
                    boolQueryBuilder.should(wildcardQueryBuilder1);
                }
            }else{
            WildcardQueryBuilder wildcardQueryBuilder= QueryBuilders.wildcardQuery("question_description","*"+target+"*");
            WildcardQueryBuilder wildcardQueryBuilder1= QueryBuilders.wildcardQuery("question_detail","*"+target+"*");
            boolQueryBuilder.should(wildcardQueryBuilder);
            boolQueryBuilder.should(wildcardQueryBuilder1);
                    //QueryBuilders.moreLikeThisQuery(fileds,text,null);
            }

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            searchRequest.source(sourceBuilder);

            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            //打包
            List<Map<String,Object>> list=new ArrayList<>();
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
                list.add(documentFields.getSourceAsMap());

            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
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
            searchSourceBuilder.size(2000);
            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.ASC);

            MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("question_tags",tags).operator(Operator.AND);
            searchSourceBuilder.query(termQueryBuilder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            List<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to search");
        }
    }

    @Override
    public ResponseMessage answerSort(String flag) {
        try {

            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            FieldSortBuilder fsb;
            if(flag.equals("a"))
                fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.DESC);
            else
                fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.ASC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();
            searchSourceBuilder.query(builder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            ArrayList<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {

                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to answerSort");
        }
    }

    @Override
    public ResponseMessage timeSort(String flag) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);

            FieldSortBuilder fsb;
            if(flag.equals("a"))
                fsb= SortBuilders.fieldSort("create_time").order(SortOrder.DESC);
            else
                fsb= SortBuilders.fieldSort("create_time").order(SortOrder.ASC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();

            searchSourceBuilder.query(builder).sort(fsb);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            ArrayList<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to timeSort");
        }
    }

    @Override
    public ResponseMessage likesSort(String flag) {
        try {
            SearchRequest searchRequest = new SearchRequest("questiones");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(2000);
            FieldSortBuilder fsb;
            if(flag.equals("a"))
                fsb= SortBuilders.fieldSort("likes").order(SortOrder.DESC);
            else
                fsb= SortBuilders.fieldSort("likes").order(SortOrder.ASC);
            QueryBuilder builder=QueryBuilders.matchAllQuery();
            searchSourceBuilder.query(builder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            ArrayList<Map<String,Object>> list=new ArrayList<>();

            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
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
            searchSourceBuilder.size(2000);
            FieldSortBuilder fsb= SortBuilders.fieldSort("number_comment").order(SortOrder.ASC);

            MatchPhraseQueryBuilder termQueryBuilder = QueryBuilders.matchPhraseQuery("question_tags",question_tags);
            searchSourceBuilder.query(termQueryBuilder).sort(fsb);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            ArrayList<Map<String,Object>> list=new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            return responseMessage;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseMessage.fail(question_tags);
        }
    }



}
