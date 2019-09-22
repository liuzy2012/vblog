package com.vi3nty.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.vo.CommentVo;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 21:30 2019/9/21
 */
@Controller
@RequestMapping("/admin/comments")
public class CommentController {
    private static final int pageSize=5;
    @Autowired
    private ICommentService iCommentService;

    @GetMapping("/all")
    public String getAllComments(Model model,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<CommentVo> iPage=iCommentService.allComments(currentPage,pageSize);
        model.addAttribute("commlist",iPage);
        return "admin/admin-comments";
    }
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ServerResponse delComment(@PathVariable("id") int id){
        int result=iCommentService.delComment(id);
        if(result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }
}
