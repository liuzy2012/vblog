package com.vi3nty.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.vi3nty.blog.entity.Message;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.service.IMessageService;
import com.vi3nty.blog.service.IUserService;
import com.vi3nty.blog.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author : vi3nty
 * @date : 16:37 2019/10/17
 */
@Controller
@RequestMapping("/admin/notice")
public class NoticeController implements Constant {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;

    @GetMapping("/comment")
    public String commentsNotice(Model model,HttpSession session){
        User user= (User) session.getAttribute("userlogin");

        //查询评论类通知
        Message message=messageService.selectLastNotice(user.getId(),TOPIC_COMMENT);
        int msgCount=messageService.selectNoticeCount(user.getId(),TOPIC_COMMENT);
        int msgUnreadedCount=messageService.selectNoticeUnreadedCount(user.getId(),TOPIC_COMMENT);
        Map<String,Object> map=JSONObject.parseObject(message.getContent(),HashMap.class);
        Map<String,Object> msgVo=new HashMap<>();
        msgVo.put("msgCount",msgCount);
        msgVo.put("msgUnreadedCount",msgUnreadedCount);
        //给哪位用户
        msgVo.put("user",userService.getUserById((Integer) map.get("userId")).getData().getUsername());
        msgVo.put("entityType",map.get("entityType"));
        //用户对哪篇文章的id进行的评论
        msgVo.put("entityId",map.get("entityId"));
        model.addAttribute("comment_detail",msgVo);
        return "admin/admin-comment-notice";
    }
    @GetMapping("/comment/detail")
    public String commentDetail(Model model, HttpSession session, @RequestParam("type") String type){
        User user=(User)session.getAttribute("userlogin");
        List<Message> list=messageService.allUnreadedNotice(user.getId(),type);
        Map<String,Object> map;
        List<MsgInfo> msglist=new LinkedList<>();
        List<Integer> ids=new LinkedList<>();
        for(Message msg:list){
            MsgInfo msgInfo=new MsgInfo();
            msgInfo.setName(userService.getUserById((Integer) JSONObject.parseObject(msg.getContent(),HashMap.class).get("userId")).getData().getUsername());
            msgInfo.setAid((Integer) JSONObject.parseObject(msg.getContent(),HashMap.class).get("entityId"));
            msglist.add(msgInfo);
            ids.add(msg.getId());
        }
        model.addAttribute("msglist",msglist);
        //将通知状态设为已读
        int result=messageService.updateStatus(ids,1);
        if(result==1)
            session.setAttribute("unreadNoticeCount",0);
        return "admin/admin-comment-notice-detail";
    }
    private class MsgInfo{
        private String name;
        private int aid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }
    }
}
