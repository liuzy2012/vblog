package com.vi3nty.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author : vi3nty
 * @date : 13:34 2019/9/19
 */
@TableName("user_role")
public class User_Role {
    @TableField("user_id")
    private int UserId;
    @TableField("role_id")
    private int Role_Id;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
    }
}
