package com.vi3nty.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.vo.CommentVo;
import com.vi3nty.blog.mapper.CommentMapper;
import com.vi3nty.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 20:50 2019/9/21
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public IPage<CommentVo> allComments(int currentPage, int pageSize) {
        IPage<Article> page=new Page<>(currentPage,pageSize);
        return commentMapper.getAllComment(page);
    }

    @Override
    public int delComment(int id) {
        return commentMapper.deleteById(id);
    }
}
