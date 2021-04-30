package com.example.demo.service.QuestionService;

import com.example.demo.dao.QuestionDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.es.QuestionEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class QuestionPublishToEsService {
    @Autowired
    QuestionDao questionDao;

    public boolean publishQuestion(Question question, List<String> files){
        if (question==null){
            QuestionEs questionEs = new QuestionEs();
            questionEs.setId(question.getQuestion_id());
            questionEs.setUser_id(question.getUser_id());
            questionEs.setUser_name(question.getUser_name());
            questionEs.setQuestion_description(question.getQuestion_description());
            questionEs.setQuestion_detail(question.getQuestion_detail());
            questionEs.setQuestion_tags(question.getQuestion_tags());
            questionEs.setNumber_comment(question.getNumber_comment());
            questionEs.setLikes(question.getLikes());
            questionEs.setCreate_time(question.getTime());
            questionEs.setPics(files);
            questionDao.save(questionEs);
        }
        return false;
    }

    public boolean publishQuestion(Question question){
        if (question!=null){
            QuestionEs questionEs = new QuestionEs();
            questionEs.setId(question.getQuestion_id());
            questionEs.setUser_id(question.getUser_id());
            questionEs.setUser_name(question.getUser_name());
            questionEs.setQuestion_description(question.getQuestion_description());
            questionEs.setQuestion_detail(question.getQuestion_detail());
            questionEs.setQuestion_tags(question.getQuestion_tags());
            questionEs.setNumber_comment(question.getNumber_comment());
            questionEs.setLikes(question.getLikes());
            questionEs.setCreate_time(question.getTime());

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
            throw new RuntimeException("Comment on failure！");
        }
        questionEs.addCommentNumber();
//        questionDao.deleteById();
        questionDao.save(questionEs);

        return true;
    }

    public boolean likesAsync(String question_id, int sum){
        Optional<QuestionEs> byId = questionDao.findById(question_id);
        if (!byId.isPresent()){
            throw new RuntimeException("The question doesn't exist！");
        }
        QuestionEs questionEs = byId.get();
        questionEs.setLikes(sum);
        questionDao.save(questionEs);
        return true;
    }


}
