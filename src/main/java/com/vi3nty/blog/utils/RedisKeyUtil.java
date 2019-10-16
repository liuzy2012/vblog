package com.vi3nty.blog.utils;

/**
 * @author : vi3nty
 * @date : 20:09 2019/10/11
 */
public class RedisKeyUtil {

    private static final String SPLIT=":";
    private static final String PREFIX_ENTITY_LIKE="like:entity";
    private static final String PREFIX_KAPTCHA="kaptcha";
    private static final String PREFIX_TICKET="ticket";

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

    /**
     * 用户登录验证码
     * @param owner 用户临时身份凭证
     * @return
     */
    public static String getKaptchaKey(String owner){
        return PREFIX_KAPTCHA+SPLIT+owner;
    }

    /**
     * 登录凭证
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket){
        return PREFIX_KAPTCHA+SPLIT+ticket;
    }
}
