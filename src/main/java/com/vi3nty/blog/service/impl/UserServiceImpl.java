package com.vi3nty.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vi3nty.blog.entity.Role;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.mapper.UserMapper;
import com.vi3nty.blog.service.IUserService;
import com.vi3nty.blog.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Collection;
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
public class UserServiceImpl implements UserDetailsService,IUserService, Constant {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${blog.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String path;

    @Override
    public ServerResponse<User> userLogin(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", user.getEmail());
        wrapper.eq("password", user.getPassword());
        User loginUser = userMapper.selectOne(wrapper);
        if (loginUser != null) {
            Authentication authentication=new UsernamePasswordAuthenticationToken(
                    user,user.getPassword(),this.getAuthorities(user.getId()));
            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            return ServerResponse.createBySuccess(loginUser);
        }
        return ServerResponse.createByErrorMessage("账号密码错误");
    }

    @Override
    public ServerResponse<User> getUserById(int id) {
        return ServerResponse.createBySuccess(userMapper.selectById(id));
    }

    @Override
    public ServerResponse delUser(int uid) {
        int result=userMapper.deleteById(uid);
        if(result==1)
            return ServerResponse.createBySuccessMessage("删除成功");
        else
            return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateUserStatus(int uid,int status) {
        User user=new User(uid,status);
        int result=userMapper.updateById(user);
        if(result==1)
            return ServerResponse.createBySuccessMessage("状态更新成功");
        else
            return ServerResponse.createByErrorMessage("状态更新失败");
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

    @Override
    public ServerResponse addUser(User user) {
        if(StringUtils.isBlank(user.getEmail()))
            return ServerResponse.createByErrorCodeMessage(ResponseCode.EMAIL_BLANK.getCode(),ResponseCode.EMAIL_BLANK.getDesc());
        if(StringUtils.isBlank(user.getUsername()))
            return ServerResponse.createByErrorCodeMessage(ResponseCode.USERNAME_BLANK.getCode(),ResponseCode.USERNAME_BLANK.getDesc());
        if(StringUtils.isBlank(user.getPassword()))
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PASSWORD_BLANK.getCode(),ResponseCode.PASSWORD_BLANK.getDesc());
        //验证账号邮箱是否注册
        User u=getByEmail(user.getEmail());
        if(u!=null)
            return ServerResponse.createByErrorCodeMessage(ResponseCode.EMAIL_REPET.getCode(),ResponseCode.EMAIL_REPET.getDesc());
        //注册用户
        user.setSalt(BlogUtil.generateUUID().substring(0,5));
        user.setPassword(BlogUtil.md5(user.getPassword()+user.getSalt()));
        user.setUserType(1);
        user.setStatus(0);
        user.setActivationCode(BlogUtil.generateUUID());
        userMapper.insert(user);
        //激活邮件
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        context.setVariable("url","http://localhost:8080/blog/active/"+user.getId()+"/"+user.getActivationCode());
        String content=templateEngine.process("/portal/activation",context);
        mailClient.sendMail("liuzhaoyang94@163.com","激活账号",content);
        return ServerResponse.createBySuccess("注册成功",user);
    }

    /**
     * 构造函数可减少mysql消耗开支
     * @param uid
     * @param code
     * @return
     */
    @Override
    public int activeUser(int uid,String code) {
        User user=userMapper.selectById(uid);
        if(code.equals(user.getActivationCode()))
            return userMapper.updateById(new User(uid,1));
        return 0;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(int userId){
        User user=userMapper.selectById(userId);
        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getUserType()){
                    case 1:
                        return AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_USER;
                    default:
                        return AUTHORITY_ADMIN;
                }
            }
        });
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getByEmail(s);
    }
}
