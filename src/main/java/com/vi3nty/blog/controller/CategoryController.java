package com.vi3nty.blog.controller;

import com.vi3nty.blog.service.ICategoryService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : vi3nty
 * @date : 21:20 2019/9/18
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final int pageSize=5;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("/all")
    @ResponseBody
    public ServerResponse getAllCategory(){
        return ServerResponse.createBySuccess(iCategoryService.getAll());
    }
    @GetMapping("/admin/all")
    public String getCategoriesByPage(Model model,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        model.addAttribute("catelist",iCategoryService.getCategoriesByPage(currentPage,pageSize));
        return "admin/admin-category";
    }
}
