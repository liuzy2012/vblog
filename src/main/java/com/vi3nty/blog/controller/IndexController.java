package com.vi3nty.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : vi3nty
 * @date : 16:12 2019/9/20
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "redirect:/portal/index";
    }
    @RequestMapping("/login")
    public String tologin(HttpSession session){
        if(session.getAttribute("userlogin")!=null)
            return "admin/admin-index";
        return "admin/login";
    }
    @RequestMapping("/ulogout")
    public String logout(HttpSession session){
        if(session.getAttribute("userlogin")!=null){
            session.removeAttribute("userlogin");
            return "redirect:/login";
        }
        return "redirect:/login";
    }
    @RequestMapping("/register")
    public String toregister(){
        return "admin/register";
    }
}
