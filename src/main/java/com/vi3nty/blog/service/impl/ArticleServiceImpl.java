package com.vi3nty.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Tag;
import com.vi3nty.blog.entity.vo.ArticleVo;
import com.vi3nty.blog.mapper.ArticleMapper;
import com.vi3nty.blog.mapper.TagMapper;
import com.vi3nty.blog.service.IArticleService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    /**
     * 添加文章
     * @param article controller层封装来的article对象
     * @return
     */
    @Override
    @Transactional
    public ServerResponse addArticle(Article article) {
        int result=articleMapper.insert(article);
        List<String> tagNameList= JSONObject.parseArray(article.getTags(),String.class);
        int aid=article.getId();
        if(tagNameList!=null) {
            for (String name : tagNameList) {
                QueryWrapper<Tag> wrapper = new QueryWrapper<>();
                wrapper.eq("name", name);
                if (tagMapper.selectCount(wrapper) == 0) {
                    Tag tag = new Tag(name);
                    tagMapper.insert(tag);
                    tagMapper.insertArticleTag(aid, tag.getId());
                } else {
                    int tid = tagMapper.selectOne(wrapper).getId();
                    tagMapper.insertArticleTag(aid, tid);
                }
            }
        }
        if(result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }


    @Override
    public IPage<ArticleVo> list(int currentPage, int pageSize) {
        IPage<Article> page=new Page<>(currentPage,pageSize);
        return articleMapper.getArticlesPage(page);
    }
    @Override
    public IPage<ArticleVo> listByCid(int currentPage, int pageSize, int cid) {
        IPage<ArticleVo> page=new Page<>(currentPage,pageSize);
        page.setRecords(articleMapper.getArticlesPageByCid(cid,page));
        return page;
    }

    @Override
    public Article getArticleById(int id) {
        return articleMapper.selectById(id);
    }

    @Override
    public ArticleVo getArticleVoById(int id) {
        return articleMapper.getArticleVoById(id);
    }

    @Override
    public int delete(int id) {
        return articleMapper.deleteById(id);
    }

    @Override
    public int updateArt(Article article) {
        return articleMapper.updateById(article);
    }

    @Override
    public int updateArticleDraftStatus(int aid, int status) {
        return articleMapper.updateArticleDraftStatus(aid,status);
    }


}
