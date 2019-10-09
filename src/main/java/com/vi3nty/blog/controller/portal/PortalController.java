package com.vi3nty.blog.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.entity.vo.CommentVo;
import com.vi3nty.blog.service.IArticleService;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/**
 * @author : vi3nty
 * @date : 20:40 2019/9/18
 */
@Controller
@RequestMapping("/portal")
public class PortalController {
    private static final int pageSize=5;

    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private ICommentService iCommentService;

    @GetMapping("/index")
    public String articlelist(Model model, @RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.list(currentPage,pageSize);
        model.addAttribute("artlist",iPage);
        return "portal/index";
    }

    @GetMapping("/detail/{aid}")
    public String artDetail(Model model, HttpServletRequest request, @PathVariable("aid") int aid){
        ArticleVo articleVo=iArticleService.getArticleVoById(aid);
        if(articleVo==null)
            return "error/404";
        IPage<CommentVo> iComment=iCommentService.getCommentsByAid(aid,1,10);
        model.addAttribute("art",articleVo);
        model.addAttribute("comments",iComment);
        return "portal/detail";
    }
    @PostMapping("/detail/{aid}/commentpost")
    public ServerResponse commentPost(@PathVariable("aid") int aid,Comment comment){
        return iCommentService.postComment(aid,comment);
    }
}
