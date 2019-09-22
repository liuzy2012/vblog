package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author : vi3nty
 * @date : 17:45 2019/9/21
 * 文章评论实体类
 */
@TableName(value = "comment")
public class Comment {
    @TableField("id")
    private int id;
    @TableField("comm_email")
    private String commEmail;
    @TableField("content")
    private String content;
    @TableField("aid")
    private int aid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommEmail() {
        return commEmail;
    }

    public void setCommEmail(String commEmail) {
        this.commEmail = commEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
