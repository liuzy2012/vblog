package com.vi3nty.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.service.IArticleService;
import com.vi3nty.blog.service.impl.ITagService;
import com.vi3nty.blog.utils.Constant;
import com.vi3nty.blog.utils.HtmlParse;
import com.vi3nty.blog.utils.SensitiveFilter;
import com.vi3nty.blog.utils.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : vi3nty
 * @date : 20:40 2019/9/18
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController implements Constant {

    private static final Logger LOGGER=LoggerFactory.getLogger(ArticleController.class);

    private static final int pageSize=5;

    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private ITagService iTagService;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private HtmlParse htmlParse;

    /**
     * 文章发布
     * @param title
     * @param content
     * @param uid
     * @return
     */
    @PostMapping("/{uid}/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse add(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("draft") int draft,@RequestParam("summary")String summary,@RequestParam("tags")String tags,@RequestParam("cid")String cid,@PathVariable("uid") Integer uid) {
        //敏感词过滤
        content=sensitiveFilter.filter(content);
        Article article=new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDraft(draft);
        if(StringUtils.isNotBlank(summary))
            article.setSummary(summary);
        else{
            String text=htmlParse.htmlToText(content);
            String summary1=text.length()>30?text.substring(0,30):text;
            article.setSummary(summary1+"...");
        }
        List<String> tagNameList= JSONObject.parseArray(tags,String.class);
        if(StringUtils.isNotBlank(tags)&&tagNameList!=null) {
            article.setTags(tags);
        }
        else {
            article.setTags("");
        }
        article.setUid(uid);
        article.setCid(Integer.parseInt(cid));
        ServerResponse response=null;
        try {
            response=iArticleService.addArticle(article);
            //触发发帖事件
            Event event=new Event();
            event.setTopic(TOPIC_PUBLISH);
            event.setUserId(uid);
            event.setEntityId(article.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    //管理端文章更新
    @PostMapping("/{aid}/update")
    @ResponseBody
    public ServerResponse update(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("draft") int draft,@RequestParam("summary")String summary,@RequestParam("tags")String tags,@RequestParam("cid")String cid,@PathVariable("aid") Integer aid){
        content=sensitiveFilter.filter(content);
        Article article=new Article();
        article.setId(aid);
        article.setTitle(title);
        article.setContent(content);
        article.setDraft(draft);
        if(StringUtils.isNotBlank(summary))
            article.setSummary(summary);
        else{
            String text=htmlParse.htmlToText(content);
            String summary1=text.length()>30?text.substring(0,30):text;
            article.setSummary(summary1+"...");
        }
        List<String> tagNameList= JSONObject.parseArray(tags,String.class);
        if(StringUtils.isNotBlank(tags)&&tagNameList!=null) {
            article.setTags(tags);
        }
        else {
            article.setTags("");
        }
        article.setCid(Integer.parseInt(cid));
        int result=iArticleService.updateArt(article);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    //管理端文章列表处理文章草稿状态更新

    @GetMapping("/update/draft/{aid}/{status}")
    @ResponseBody
    public ServerResponse updateArticleDraft(@PathVariable("aid") int aid,@PathVariable("status") int status){
        int result=iArticleService.updateArticleDraftStatus(aid,status);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    //管理端文章列表页
    @GetMapping("/list")
    public String articlelist(Model model,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.list(currentPage,pageSize);
        model.addAttribute("articles",iPage);
        return "new/admin/pages/article/list";
    }
    //管理端文章列表分类
    @GetMapping("/list/{cid}")
    public String articlelistByCid(Model model,@PathVariable("cid") int cid,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.listByCid(currentPage,pageSize,cid);
        model.addAttribute("articles",iPage);
        return "admin/admin-articlescid";
    }
    @GetMapping("/edit/{aid}")
    public String articleDetail(Model model,@PathVariable("aid") int aid){
        ArticleVo articleVo=iArticleService.getArticleVoById(aid);
        articleVo.setContent(articleVo.getContent());
        model.addAttribute("art",articleVo);
        return "new/admin/pages/article/art-edit";
    }
    @GetMapping("/show/{aid}")
    public String showArticle(Model model,@PathVariable("aid") int aid){
        ArticleVo articleVo=iArticleService.getArticleVoById(aid);
        articleVo.setContent(articleVo.getContent());
        model.addAttribute("art",articleVo);
        return "new/admin/pages/article/art-show";
    }
    @DeleteMapping("delete/{aid}")
    @ResponseBody
    public ServerResponse articleDelete(@PathVariable("aid")int aid){
        int result=iArticleService.delete(aid);
        //触发删帖事件
        Event event=new Event();
        event.setTopic(TOPIC_DEL);
        event.setEntityId(aid);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    @PostMapping("/upload")
    @ResponseBody
    public Map<Object,Object> upload(HttpServletRequest request, HttpServletResponse response,MultipartFile attach) throws UnsupportedEncodingException {

        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        String fileName=System.currentTimeMillis()+attach.getOriginalFilename().substring(attach.getOriginalFilename().lastIndexOf("."));
        File imageFolder=new File(request.getServletContext().getRealPath("img/post/"));
        String destFileName=request.getServletContext().getRealPath("img/post/")+File.separator+fileName;

        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        File destFile=new File(destFileName);

        Map<Object,Object> map=new HashMap<>();
        try {
            attach.transferTo(destFile);
            map.put("success",1);
            map.put("message","上传成功");
            map.put("location","/blog/img/post/"+fileName);
        } catch (IOException e) {
            map.put("success",0);
           e.printStackTrace();
        }
        return map;
    }


}
