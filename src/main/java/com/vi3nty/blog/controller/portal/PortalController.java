package com.vi3nty.blog.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/index")
    public String articlelist(Model model, @RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.list(currentPage,pageSize);
        model.addAttribute("artlist",iPage);
        return "portal/index";
    }

    @GetMapping("/detail/{aid}")
    public String artDetail(Model model, HttpServletRequest request, @PathVariable("aid") int aid){
        ArticleVo articleVo=iArticleService.getArticleVoById(aid);
        model.addAttribute("art",articleVo);
        return "portal/detail";
    }
}
