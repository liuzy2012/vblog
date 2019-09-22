package com.vi3nty.blog.utils;

import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : vi3nty
 * @date : 22:29 2019/9/19
 */
public class BlogRealm extends AuthorizingRealm {

    @Autowired
    private IUserService iUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleSets=new HashSet<>();
        roleSets.add(iUserService.getRole(username).getName());
        // 给该用户设置角色，角色信息存在 t_role 表中取
        authorizationInfo.setRoles(roleSets);
        Set<String> permissionSets=new HashSet<>();
        permissionSets.add(iUserService.getPermission(username));
        // 给该用户设置权限，权限信息存在 t_permission 表中取
        authorizationInfo.setStringPermissions(permissionSets);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据 Token 获取用户名
        String email = (String) authenticationToken.getPrincipal();
        // 根据用户名从数据库中查询该用户
        User user = iUserService.getByEmail(email);
        if(user != null) {
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            // 传入用户名和密码进行身份认证，并返回认证信息
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "BlogRealm");
            return authcInfo;
        } else {
            return null;
        }
    }
}
