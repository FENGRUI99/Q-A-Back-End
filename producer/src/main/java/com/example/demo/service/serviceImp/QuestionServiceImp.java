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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
            MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("question_description",target);
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
    public ResponseMessage listbyTag(String tags) {
        try {
            tags.replace(',','%');
            List<Question> questions=mapper.listbyTag(tags);
             ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(questions);
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
