package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

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
    @TableField("entity_type")
    private String entityType;
    @TableField("create_time")
    private Date createTime;
    @TableField("status")
    private int status;

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

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
