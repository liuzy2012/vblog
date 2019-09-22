package com.vi3nty.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 20:49 2019/9/21
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.id as id,c.content as content,c.comm_email as comm_email,a.id as aid,a.title as art_title from comment c LEFT JOIN article a on c.aid=a.id")
    IPage<CommentVo> getAllComment(IPage iPage);
}
