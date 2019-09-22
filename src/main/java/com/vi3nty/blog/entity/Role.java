package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author : vi3nty
 * @date : 13:33 2019/9/19
 */
@TableName("role")
public class Role {
    private int id;
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
