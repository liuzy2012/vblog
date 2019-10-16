package com.vi3nty.blog.controller;

import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.service.ILikeService;
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
public class LikeController {

    @Autowired
    private ILikeService iLikeService;

    @PostMapping("/like")
    @ResponseBody
    public ServerResponse<Map<String,Object>> like(HttpSession session,int entityType, int entityId){
        User user= (User) session.getAttribute("userlogin");
        iLikeService.like(user.getId(),entityType,entityId);
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
