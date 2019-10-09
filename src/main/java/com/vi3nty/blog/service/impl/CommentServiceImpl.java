package com.vi3nty.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.vo.CommentVo;
import com.vi3nty.blog.mapper.CommentMapper;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.utils.ServerResponse;
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
        IPage<CommentVo> page=new Page<>(currentPage,pageSize);
        return commentMapper.getAllComment(page);
    }

    @Override
    public IPage<CommentVo> getCommentsByAid(int aid, int currentPage, int pageSize) {
        IPage<CommentVo> page=new Page<>(currentPage,pageSize);
        return commentMapper.getCommentByAid(page,aid);
    }

    @Override
    public int delComment(int id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public ServerResponse postComment(int aid, Comment comment) {
        comment.setAid(aid);
        int result=commentMapper.insert(comment);
        if (result==1)
            return ServerResponse.createBySuccess();
        return ServerResponse.createByError();
    }
}
