package com.vi3nty.blog.service.impl;

import com.vi3nty.blog.service.ILikeService;
import com.vi3nty.blog.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author : vi3nty
 * @date : 20:19 2019/10/11
 */
@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId,int entityType,int entityId){
        String entityLikeKey=RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        //判断是否已经点赞
        boolean isMember=redisTemplate.opsForSet().isMember(entityLikeKey,userId);
        if(isMember){
            //取消赞
            redisTemplate.opsForSet().remove(entityLikeKey,userId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey,userId);
        }
    }

    //查询某实体点赞的数量
    public long getEntityLikeCount(int entityType,int entityId){
        String entityLikeKey=RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId,int entityType,int entityId){
        String entityLikeKey=RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
    }
}
