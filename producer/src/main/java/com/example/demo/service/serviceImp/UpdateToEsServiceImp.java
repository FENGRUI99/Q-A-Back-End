package com.example.demo.service.serviceImp;

import com.example.demo.dao.QuestionDao;

import com.example.demo.pojo.QuestionEs;
import com.example.demo.service.service.UpdateToEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateToEsServiceImp implements UpdateToEsService {

    @Autowired
    QuestionDao questionDao;

    @Override
    public boolean UpdateToLikeEsService(String question_id,int sum) {
        Optional<QuestionEs> byId = questionDao.findById(question_id);
        if (!byId.isPresent()){
            throw new RuntimeException("The comment issue doesn't exist！");
        }
        QuestionEs questionEs = byId.get();
        questionEs.setLikes(sum);
        questionDao.save(questionEs);
        return true;
    }
}
