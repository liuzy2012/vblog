package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author : vi3nty
 * @date : 21:15 2019/9/18
 */
@TableName("category")
public class Category {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
