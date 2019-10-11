package com.vi3nty.blog.service;

import com.vi3nty.blog.utils.RedisKeyUtil;

/**
 * @author : vi3nty
 * @date : 20:19 2019/10/11
 */
public interface ILikeService {
    void like(int userId,int entityType,int entityId);
    long getEntityLikeCount(int entityType,int entityId);
    int findEntityLikeStatus(int userId,int entityType,int entityId);
}
