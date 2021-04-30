package com.example.demo;

import com.example.demo.dao.QuestionDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.es.QuestionEs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
class DemoApplicationTests {
//	@Autowired
//	QuestionDao questionDao;
//
//
//	@Test
//	void contextLoads() {
//		QuestionEs questionEs = new QuestionEs();
//		questionEs.setId(UUID.randomUUID().toString());
//		questionEs.setUser_name("小白");
//		ArrayList<Comment> objects = new ArrayList<>();
//		Comment comment = new Comment();
//		comment.setComment_detail("12321312312213");
//		objects.add(comment);
//		questionEs.setComments(objects);
//
//		questionDao.save(questionEs);
//
//
//	}

}
