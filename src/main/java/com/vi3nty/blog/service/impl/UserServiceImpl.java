package com.vi3nty.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Role;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.mapper.UserMapper;
import com.vi3nty.blog.service.IUserService;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vi3nty
 * @since 2019-09-10
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> userLogin(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", user.getEmail());
        wrapper.eq("password", user.getPassword());
        User loginUser = userMapper.selectOne(wrapper);
        if (loginUser != null)
            return ServerResponse.createBySuccess(loginUser);
        return ServerResponse.createByErrorMessage("账号密码错误");
    }

    @Override
    public ServerResponse<User> getUserById(int id) {
        return ServerResponse.createBySuccess(userMapper.selectById(id));
    }


    @Override
    public IPage<User> getUserByPage(int currentPage,int pageSize) {
        IPage<User> page = new Page<>(currentPage, pageSize);
        return userMapper.selectPage(page, null);
    }

    @Override
    public Role getRole(String email) {
        return userMapper.getRole(email);
    }

    @Override
    public String getPermission(String username) {
        return userMapper.getPermission(username);
    }

    @Override
    public User getByEmail(String username) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("email",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

}
