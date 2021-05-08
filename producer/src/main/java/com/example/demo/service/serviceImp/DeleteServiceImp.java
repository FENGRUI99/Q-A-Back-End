package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.dao.QuestionDao;
import com.example.demo.mapper.DeleteMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.QuestionEs;
import com.example.demo.service.service.DeleteService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.Optional;

@Service
public class DeleteServiceImp implements DeleteService {

    @Resource
    DeleteMapper deleteMapper;

    @Autowired
    QuestionDao questionDao;


    @Override
    public ResponseMessage deleteQuestion(String question_id) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("121.5.27.43", 9200, "http")));
        //参数为索引名，可以不指定，可以一个，可以多个
        DeleteByQueryRequest request = new DeleteByQueryRequest("questiones");
        // 更新时版本冲突
        request.setConflicts("proceed");
        // 设置查询条件，第一个参数是字段名，第二个参数是字段的值
        request.setQuery(new TermQueryBuilder("question_id", question_id));
        // 更新最大文档数
        request.setSize(10);
        // 批次大小
        request.setBatchSize(1000);
        // 并行
        request.setSlices(2);
        // 使用滚动参数来控制“搜索上下文”存活的时间
        request.setScroll(TimeValue.timeValueMinutes(10));
        // 超时
        request.setTimeout(TimeValue.timeValueMinutes(2));
        // 刷新索引
        request.setRefresh(true);
        try {
            deleteMapper.deleteQuestion(question_id);
            BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
            ResponseMessage responseMessage=ResponseMessage.success();
            responseMessage.setEntity(response.getStatus().getUpdated());
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseMessage.fail("fail to delete question");
        }
    }

    @Override
    public ResponseMessage deleteComment(Comment comment) {
        try {
            Optional<QuestionEs> byId = questionDao.findById(comment.getQuestion_id());

        if (!byId.isPresent()){
            throw new RuntimeException("The comment issue doesn't exist！");
        }
        QuestionEs questionEs = byId.get();
        List<Comment> commentList = questionEs.getCommentList();
        commentList.remove(comment);
        questionEs.setCommentList(commentList);
        questionDao.save(questionEs);
        deleteMapper.deleteComment(comment.getComment_id());
        ResponseMessage responseMessage=ResponseMessage.success();
        return responseMessage;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseMessage.fail("fail to delete comment");
        }

    }
}
