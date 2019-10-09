package com.vi3nty.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.vo.CommentVo;
import com.vi3nty.blog.utils.ServerResponse;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 20:49 2019/9/21
 */
public interface ICommentService {

    IPage<CommentVo> allComments(int currentPage, int pageSize);
    IPage<CommentVo> getCommentsByAid(int aid,int currentPage, int pageSize);
    int delComment(int id);
    ServerResponse postComment(int aid, Comment comment);
}
