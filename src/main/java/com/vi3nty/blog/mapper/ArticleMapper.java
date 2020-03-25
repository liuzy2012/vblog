package com.vi3nty.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select ar.id as id,ar.title as title,ar.content as content,ar.summary as summary,ar.cid as cid,draft as draft,c.name as category,u.id as uid,u.username as authName,ar.create_time as create_time from article ar LEFT JOIN category c on ar.cid=c.id LEFT JOIN user u on ar.uid=u.id order by ar.id")
    IPage<ArticleVo> getArticlesPage(IPage iPage);

    @Select("select ar.id as id,ar.title as title,ar.content as content,ar.summary as summary,ar.cid as cid,ar.tags as tags,ar.draft as draft,c.name as category,u.id as uid,u.username as authName,ar.create_time as create_time from article ar LEFT JOIN category c on ar.cid=c.id LEFT JOIN user u on ar.uid=u.id where ar.id=#{aid}")
    ArticleVo getArticleVoById(int aid);

    @Select("select ar.id as id,ar.title as title,ar.content as content,ar.summary as summary,ar.cid as cid,c.name as category,u.id as uid,u.username as authName,ar.create_time as create_time from article ar LEFT JOIN category c on ar.cid=c.id LEFT JOIN user u on ar.uid=u.id where ar.cid=#{cid} order by ar.id")
    List<ArticleVo> getArticlesPageByCid(@Param("cid") int cid, IPage iPage);
    @Update("update article SET draft = #{status} where id = #{aid}")
    int updateArticleDraftStatus(@Param("aid") int aid,@Param("status") int status);
}
