package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.QuestionService;
import org.apache.lucene.index.Term;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionServiceImp implements QuestionService {

    @Resource
    QuestionMapper mapper;

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Override
    public ResponseMessage listQuestion(String id) {
        try {
            List<Question> question = mapper.listQuestion(id);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(question);
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
            //匹配目标


            //实现高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("question_description");
            highlightBuilder.requireFieldMatch(false);  //多个单词高亮的话，要把这个设置为trues
            highlightBuilder.preTags("<font color='#dd4b39'>");
            highlightBuilder.postTags("</font>");
            searchSourceBuilder.highlighter(highlightBuilder);

            MultiMatchQueryBuilder termQueryBuilder = QueryBuilders.multiMatchQuery(target,"question_description" ,"question_details");
            searchSourceBuilder.query(termQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //开始搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            //打包
            ArrayList<Map<String,Object>> list = new ArrayList<>();
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                Map<String, HighlightField> highlight =documentFields.getHighlightFields();
                HighlightField title=highlight.get("question_description");
                Map<String, Object> source=documentFields.getSourceAsMap();
                if(title!=null){
                    Text[] fragments=title.fragments();
                    String newTitle="";
                    for (Text fragment : fragments) {
                        newTitle+=fragment;
                    }
                    source.put("question_description",newTitle);
                }
                list.add(documentFields.getSourceAsMap());
            }
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(list);
            System.out.println(list.size());
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

            MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("question_tags",tags);
            searchSourceBuilder.query(termQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);

            ArrayList<Map<String,Object>> list = new ArrayList<>();
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
    public ResponseMessage answerSort() {
        try {
            GetIndexRequest request = new GetIndexRequest("question");
            boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);

            List<Question>   questions = mapper.answerSort();
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(questions);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to answerSort");
        }
    }

    @Override
    public ResponseMessage timeSort() {
        try {
            List<Question>   questions = mapper.timeSort();
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(questions);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to timeSort");
        }
    }

    @Override
    public ResponseMessage likesSort() {
        try {
            List<Question>   questions = mapper.likesSort();
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(questions);
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to likesSort");
        }
    }

    @Override
    public ResponseMessage relevant(String question_tags) {
        try {
            String [] temp = question_tags.split("\\s+");
            Random rand = new Random();
            int x = rand.nextInt(temp.length);
            String relevant = temp[x];
            List<Question> questions = mapper.relevant(relevant);
            ResponseMessage responseMessage = ResponseMessage.success();
            responseMessage.setEntity(questions);
            return responseMessage;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseMessage.fail(question_tags);
        }
    }


}
