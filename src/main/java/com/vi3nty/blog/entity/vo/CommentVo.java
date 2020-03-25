package com.vi3nty.blog.entity.vo;

/**
 * @author : vi3nty
 * @date : 20:54 2019/9/21
 */
public class CommentVo {

    private int id;
    private String content;
    private String commEmail;
    private String commAuthor;
    private int aid;
    private String artTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommEmail() {
        return commEmail;
    }

    public void setCommEmail(String commEmail) {
        this.commEmail = commEmail;
    }

    public String getCommAuthor() {
        return commAuthor;
    }

    public void setCommAuthor(String commAuthor) {
        this.commAuthor = commAuthor;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }
}
