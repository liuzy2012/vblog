package com.vi3nty.blog.controller;

import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.event.EventProducer;
import com.vi3nty.blog.service.ILikeService;
import com.vi3nty.blog.utils.Constant;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : vi3nty
 * @date : 20:52 2019/10/11
 */
@Controller
public class LikeController implements Constant {

    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private EventProducer producer;
    @PostMapping("/like")
    @ResponseBody
    public ServerResponse<Map<String,Object>> like(HttpSession session,int entityType, int entityId,int a_uid){
        User user= (User) session.getAttribute("userlogin");
        //点赞
        iLikeService.like(user.getId(),entityType,entityId);
        //触发评论事件
        Event event=new Event();
        event.setTopic(TOPIC_LIKE);
        event.setUserId(user.getId());
        event.setEntityUserId(a_uid);
        event.setEntityType(TOPIC_LIKE);
        event.setEntityId(entityId);
        producer.fireEvent(event);
        //数量
        long likeCount=iLikeService.getEntityLikeCount(entityType,entityId);
        //状态
        int likeStatus=iLikeService.findEntityLikeStatus(user.getId(),entityType,entityId);
        Map<String,Object> map=new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        return ServerResponse.createBySuccess(map);
    }
    @PostMapping("/islike")
    @ResponseBody
    public ServerResponse<Map<String,Object>> isLike(HttpSession session,int entityType, int entityId){
        User user= (User) session.getAttribute("userlogin");
        Map<String,Object> map=new HashMap<>();
        if(user==null){
            //数量
            long likeCount=iLikeService.getEntityLikeCount(entityType,entityId);
            map.put("likeCount",likeCount);
        }
        else {
            //数量
            long likeCount=iLikeService.getEntityLikeCount(entityType,entityId);
            //状态
            int likeStatus=iLikeService.findEntityLikeStatus(user.getId(),entityType,entityId);
            map.put("likeCount",likeCount);
            map.put("likeStatus",likeStatus);
        }
        return ServerResponse.createBySuccess(map);
    }
}
