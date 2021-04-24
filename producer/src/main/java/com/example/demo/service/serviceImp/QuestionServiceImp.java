package com.example.demo.service.serviceImp;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.pojo.Question;
import com.example.demo.service.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Service
public class QuestionServiceImp implements QuestionService {

    @Resource
    QuestionMapper mapper;

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
            List<Question>   questions = mapper.search(target);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(questions);
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
