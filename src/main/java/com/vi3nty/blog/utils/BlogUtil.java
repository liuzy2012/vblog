package com.vi3nty.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @author : vi3nty
 * @date : 20:41 2019/9/25
 */
public class BlogUtil {

    //生成随机字符串
    public static String generateUUID(){
        //UUID生成的字符串有-，需要去除
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密，key为原始密码+salt值
    public static String md5(String key){
        if(StringUtils.isBlank(key))
            return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
