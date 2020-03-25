package com.vi3nty.blog.controller.portal;

import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.mapper.CommentMapper;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.service.IMessageService;
import com.vi3nty.blog.service.IUserService;
import com.vi3nty.blog.utils.Constant;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : vi3nty
 * @date : 10:22 2019/10/22
 */
@Controller
@RequestMapping("/portal/comment")
public class PortalCommentController implements Constant {

    @Autowired
    private ICommentService iCommentService;


    @PostMapping("/add")
    public ServerResponse addComment(HttpSession session,int aid,String entityType,String content,int toUserId){
        User user= (User) session.getAttribute("userlogin");
        //已登录用户处理新增评论
        if(user!=null) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setEntityType(entityType);
            comment.setAid(aid);
            comment.setCommEmail(user.getEmail());
            int result = iCommentService.addComment(comment);
            //触发评论事件
            Event event=new Event();
            event.setTopic(TOPIC_COMMENT);
            event.setUserId(user.getId());
            event.setEntityUserId(toUserId);
            event.setEntityType(comment.getEntityType());
            event.setEntityId(comment.getAid());
            //获取当前用户未读通知数量
            int count= (int) session.getAttribute("unreadNoticeCount");
            session.removeAttribute("unreadNoticeCount");
            session.setAttribute("unreadNoticeCount",count+1);
            if(result==1)
                return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByError();
    }
}
