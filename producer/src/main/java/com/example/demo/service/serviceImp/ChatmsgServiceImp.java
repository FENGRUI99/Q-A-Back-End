package com.example.demo.service.serviceImp;

import com.example.demo.configuration.ResponseMessage;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.pojo.ChatBoxMessage;
import com.example.demo.pojo.ChatInfo;
import com.example.demo.pojo.ChatMsg;
import com.example.demo.pojo.chatRecords;
import com.example.demo.service.service.ChatmsgService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatmsgServiceImp implements ChatmsgService {

    @Resource
    ChatMapper mapper;

    @Resource
    StringRedisTemplate template;

    @Override
    public void insertChatmsg(ChatMsg msg) {
        mapper.insertMessage(msg.getUser_id(),msg.getSenduser_id(),msg.getName(),msg.getText(),msg.getDate());
    }

    @Override
    public ResponseMessage getChatMsg(String user_id, String senduser_id) {
       ArrayList<ChatInfo> list= mapper.getChatMsg(user_id,senduser_id);
       ResponseMessage message= ResponseMessage.success(new chatRecords(senduser_id,list));
       return message;
    }

    @Override
    public ResponseMessage getRecentChat(String request) {
        List<String> list=template.opsForList().range(request+"chatList",0,10);
        if(list.size()==0)
            return ResponseMessage.success();
        List<ChatBoxMessage> result=mapper.getRecentChat(list,request);
        for (ChatBoxMessage item : result) {
            String value= (String) template.opsForHash().get(request+"chatHash",item.getId());
            if(value==null)
                value="0";
            item.setReadNum(value);
        }
        template.expire(request+"chatHash",1, TimeUnit.MICROSECONDS);
        return ResponseMessage.success(result);
    }

    @Override
    public void setHotestChat(String user_id, String senduser_id, boolean b) {
        template.opsForList().remove(senduser_id+"chatList",10,user_id);
        template.opsForList().leftPush(senduser_id+"chatList",user_id);
        if(b)
            return;
        if(template.opsForHash().hasKey(senduser_id+"chatHash",user_id)){
            int sum=Integer.parseInt((String) template.opsForHash().get(senduser_id+"chatHash",user_id))+1;
            template.opsForHash().put(senduser_id+"chatHash",user_id,String.valueOf(sum));
        }else{
            template.opsForHash().put(senduser_id+"chatHash",user_id,1);
        }
    }
}
