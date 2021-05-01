package com.example.demo.service.serviceImp;

import com.example.demo.dao.QuestionDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.QuestionEs;
import com.example.demo.service.service.QuestionPublishToEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@Service("QuestionPublishToEsServiceImpl")
public class QuestionPublishToEsServiceImpl implements QuestionPublishToEsService {

    @Autowired
    QuestionDao questionDao;



    public boolean publishQuestion(Question question) throws ParseException {
        if (question!=null){
            QuestionEs questionEs = new QuestionEs();
            System.out.println(question.getQuestion_id());
            questionEs.setQuestion_id(question.getQuestion_id());
            questionEs.setId(question.getQuestion_id());
            questionEs.setUser_id(question.getUser_id());
            questionEs.setUser_name(question.getUser_name());
            questionEs.setQuestion_description(question.getQuestion_description());
            questionEs.setQuestion_detail(question.getQuestion_detail());
            questionEs.setQuestion_tags(question.getQuestion_tags());
            questionEs.setNumber_comment(question.getNumber_comment());
            questionEs.setLike_flag(0);
            questionEs.setLikes(question.getLikes());
            questionEs.setCreate_time(question.getTime());
            Map<String,Comment> map=new HashMap<>();
            questionEs.setCommentList(map);
            questionDao.save(questionEs);
            return true;
        }
        return false;
    }

    public boolean publishComment(Comment comment){
        Optional<QuestionEs> byId = questionDao.findById(comment.getQuestion_id());
        if (!byId.isPresent()){
            throw new RuntimeException("The comment issue doesn't exist！");
        }
        QuestionEs questionEs = byId.get();
        boolean result = questionEs.putComment(comment);
        if (!result){
            throw new RuntimeException("Comment on failure");
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comment.setCreate_time(sdf.format(date));
        Map<String,Comment> commentMap=questionEs.getCommentList();
        commentMap.put(String.valueOf(comment.getComment_id()),comment);
        questionEs.setCommentList(commentMap);
        questionEs.addCommentNumber();
        questionDao.save(questionEs);

        return true;
    }
}
