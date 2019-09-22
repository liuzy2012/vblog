package com.vi3nty.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : vi3nty
 * @date : 14:14 2019/9/19
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String toLogin(HttpSession session){
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
}
