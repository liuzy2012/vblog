package com.vi3nty.blog.controller.admin;

import com.vi3nty.blog.entity.Option;
import com.vi3nty.blog.service.IOptionService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @author : vi3nty
 * @date : 20:40 2019/9/18
 */
@Controller
@RequestMapping("/admin/option")
public class OptionController  {
    @Autowired
    private IOptionService iOptionService;

    @PostMapping("/update_anotice")
    @ResponseBody
    public ServerResponse updateAdminNotice(@RequestParam("anotice") String notice){
        int result=iOptionService.updateAdminNotice(notice);
        if(result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }
    @PostMapping("/update_pnotice")
    @ResponseBody
    public ServerResponse updatePortalNotice(@RequestParam("pnotice") String notice){
        int result=iOptionService.updatePortalNotice(notice);
        if(result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }
    @PostMapping("/update_title")
    @ResponseBody
    public ServerResponse updatePortalTitle(HttpServletRequest request, @RequestParam("title") String title){
        int result=iOptionService.updateSiteTitle(title);
        if(result==1)
            return ServerResponse.createBySuccess();

        return ServerResponse.createByError();
    }
    @PostMapping("/update_desc")
    @ResponseBody
    public ServerResponse updatePortalDesc(@RequestParam("desc") String desc){
        int result=iOptionService.updateSiteDesc(desc);
        if(result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }
    @GetMapping("/all")
    @ResponseBody
    public ServerResponse getAllOptions(HttpServletRequest request){
        List<Option> options=iOptionService.getAllOptions();
        return ServerResponse.createBySuccess(options);
    }
}
