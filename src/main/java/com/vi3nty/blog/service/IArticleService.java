package com.vi3nty.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.utils.ServerResponse;

import java.util.List;

public interface IArticleService {
    ServerResponse addArticle(Article article);
    IPage<ArticleVo> list(int currentPage, int pageSize);
    IPage<ArticleVo> listByCid(int currentPage, int pageSize, int cid);
    Article getArticleById(int id);
    ArticleVo getArticleVoById(int id);
    int delete(int id);
    int updateArt(Article article);
}
