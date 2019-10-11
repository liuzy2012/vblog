package com.vi3nty.blog.utils;

/**
 * @author : vi3nty
 * @date : 20:09 2019/10/11
 */
public class RedisKeyUtil {

    private static final String SPLIT=":";
    private static final String PREFIX_ENTITY_LIKE="like:entity";

    /**
     * 采用redis中set数据类型
     * entityType为分类 1：文章 2：用户
     * entityId为id（文章id，用户id）
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityLikeKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

}
