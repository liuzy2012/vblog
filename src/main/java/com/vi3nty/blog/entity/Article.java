package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "articlepost",type = "_doc",shards = 6,replicas = 3)
@TableName(value = "article")
public class Article {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_smart")
    @TableField("title")
    private String title;
    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_smart")
    @TableField("content")
    private String content;
    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_smart")
    @TableField("summary")
    private String summary;
    @Field(type = FieldType.Integer)
    @TableField("comment_count")
    private int commentCount;
    @Field(type = FieldType.Date)
    @TableField("create_time")
    private Date createTime;
    @Field(type = FieldType.Integer)
    @TableField("uid")
    private int uid;
    @Field(type = FieldType.Integer)
    @TableField("cid")
    private int cid;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                ", commentCount=" + commentCount +
                ", createTime=" + createTime +
                ", uid=" + uid +
                ", cid=" + cid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
