package com.vi3nty.blog.controller.admin;

import com.vi3nty.blog.service.IOptionService;
import com.vi3nty.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author : vi3nty
 * @date : 20:40 2019/9/18
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final int PAGE_SIZE=5;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOptionService iOptionService;
    @RequestMapping("")
    public String toindex(){
        return "redirect:/admin/index";
    }
    @RequestMapping("/index")
    public String index(){
        return "admin/admin-index";
    }
    @RequestMapping("/user")
    public String adminUser(Model model, @RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        model.addAttribute("userlist",iUserService.getUserByPage(currentPage,PAGE_SIZE));
        return "admin/admin-user";
    }
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
    @GetMapping("/comments")
    public String getComments(){
        return "redirect:/admin/comments/all";
    }
    @GetMapping("/category")
    public String getCategory(){
        return "redirect:/category/admin/all";
    }
    @GetMapping("/topost")
    public String topost(){
        return "admin/post";
    }
    @GetMapping("/setting")
    public String setting(Model model){
        model.addAttribute("options",iOptionService.getAllOptions());
        return "admin/admin-settings";
    }
}
