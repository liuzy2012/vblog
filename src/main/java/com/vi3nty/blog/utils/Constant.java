package com.vi3nty.blog.utils;

/**
 * @author : vi3nty
 * @date : 21:20 2019/10/16
 */
public interface Constant {

    //分类页每页文章数量
    int CATEGORY_PAGE_SIZE=5;

    /***********kafka数据类型*************/
    //实体类型：评论
    String TOPIC_COMMENT = "comment";

    //实体类型：点赞
    String TOPIC_LIKE="like";
    //主题类型：发帖
    String TOPIC_PUBLISH="publish";
    //主题类型：删帖
    String TOPIC_DEL="del";
    //系统用户ID
    int SYSTEM_USER_ID=1;
    //权限：普通用户
    String AUTHORITY_USER="USER";
    //权限：管理员
    String AUTHORITY_ADMIN="ADMIN";

}
