package com.example.demo.service.QuestionService;

import com.example.demo.dao.QuestionDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.QuestionEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */

@Service("QuestionPublishToEsServiceImpl")
public class QuestionPublishToEsServiceImpl  {

    @Autowired
    QuestionDao questionDao;



    public boolean publishQuestion(Question question) {
        if (question!=null){
            QuestionEs questionEs = new QuestionEs();
            questionEs.setQuestion_id(question.getQuestion_id());
            questionEs.setId(question.getQuestion_id());
            questionEs.setUser_id(question.getUser_id());
            questionEs.setUser_name(question.getUser_name());
            questionEs.setQuestion_description(question.getQuestion_description());
            questionEs.setQuestion_detail(question.getQuestion_detail());
            questionEs.setQuestion_tags(question.getQuestion_tags());
            questionEs.setNumber_comment(0);
            questionEs.setLike_flag("0");
            questionEs.setLikes(0);
            questionEs.setCreate_time(question.getTime());
            ArrayList<Comment> list=new ArrayList<>();
            questionEs.setCommentList(list);
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
        Date date = new Date();
        comment.setCreate_time(date.getTime());
        List<Comment> commentList=questionEs.getCommentList();
        commentList.add(comment);
        questionEs.setCommentList(commentList);
        questionEs.addCommentNumber();
        questionDao.save(questionEs);

        return true;
    }
}
