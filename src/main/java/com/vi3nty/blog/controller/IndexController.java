package com.vi3nty.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
