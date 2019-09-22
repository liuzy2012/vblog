package com.vi3nty.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vi3nty.blog.entity.Role;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.utils.ServerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vi3nty
 * @since 2019-09-10
 */
public interface IUserService {

    ServerResponse<User> userLogin(User user);
    ServerResponse<User> getUserById(int id);
    IPage<User> getUserByPage(int currentPage,int pageSize);
    Role getRole(String email);
    String getPermission(String username);
    User getByEmail(String username);
    int updateUser(User user);
}
