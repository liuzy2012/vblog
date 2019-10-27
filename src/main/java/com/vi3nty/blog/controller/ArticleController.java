package com.vi3nty.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.event.EventProducer;
import com.vi3nty.blog.service.IArticleService;
import com.vi3nty.blog.utils.Constant;
import com.vi3nty.blog.utils.HtmlParse;
import com.vi3nty.blog.utils.SensitiveFilter;
import com.vi3nty.blog.utils.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private HtmlParse htmlParse;
    @Autowired
    private EventProducer producer;
    /**
     * 文章发布
     * @param title
     * @param content
     * @param uid
     * @return
     */
    @PostMapping("/{uid}/add")
    @ResponseBody
    public ServerResponse add(@RequestParam("title")String title,@RequestParam("content")String content,@RequestParam("summary")String summary,@PathVariable("uid") Integer uid){
        //敏感词过滤
        content=sensitiveFilter.filter(content);
        Article article=new Article();
        article.setTitle(title);
//        article.setContent(htmlParse.markToHtml(content));
        article.setContent(content);
        if(StringUtils.isNotBlank(summary))
            article.setSummary(summary);
        else{
            article.setSummary(htmlParse.htmlToText(htmlParse.markToHtml(content)).substring(0,30)+"...");
        }
        article.setUid(uid);
        ServerResponse response=iArticleService.addArticle(article);
        //触发发帖事件
        Event event=new Event();
        event.setTopic(TOPIC_PUBLISH);
        event.setUserId(uid);
        event.setEntityId(article.getId());
        System.out.println("id========================"+article.getId());
        producer.fireEvent(event);

        return response;
    }
    @PostMapping("/{aid}/update")
    @ResponseBody
    public ServerResponse update(Article article){
        article.setContent(htmlParse.markToHtml(article.getContent()));
        int result=iArticleService.updateArt(article);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    @GetMapping("/list")
    public String articlelist(Model model,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.list(currentPage,pageSize);
        model.addAttribute("articles",iPage);
        return "admin/admin-articles";
    }
    @GetMapping("/list/{cid}")
    public String articlelistByCid(Model model,@PathVariable("cid") int cid,@RequestParam(name = "currentPage",defaultValue = "1") int currentPage){
        IPage<ArticleVo> iPage=iArticleService.listByCid(currentPage,pageSize,cid);
        model.addAttribute("articles",iPage);
        return "admin/admin-articlescid";
    }
    @GetMapping("/edit/{aid}")
    public String articleDetail(Model model,@PathVariable("aid") int aid){
        ArticleVo articleVo=iArticleService.getArticleVoById(aid);
        articleVo.setContent(htmlParse.htmlToMark(articleVo.getContent()));
        model.addAttribute("art",articleVo);
        return "admin/admin-editart";
    }
    @DeleteMapping("delete/{aid}")
    @ResponseBody
    public ServerResponse articleDelete(Model model,@PathVariable("aid")int aid){
        int result=iArticleService.delete(aid);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    @PostMapping("/upload")
    @ResponseBody
    public Map<Object,Object> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) throws UnsupportedEncodingException {

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
            map.put("url","/blog/img/post/"+fileName);
        } catch (IOException e) {
            map.put("success",0);
           e.printStackTrace();
        }
        return map;
    }


}
