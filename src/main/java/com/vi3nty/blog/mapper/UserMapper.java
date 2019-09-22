package com.vi3nty.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vi3nty.blog.entity.Role;
import com.vi3nty.blog.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vi3nty
 * @since 2019-09-10
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select ro.id as id,ro.name as name from role ro left join user u on u.user_type=ro.id where u.username = #{email}")
    Role getRole(String email);

    @Select("select p.pname from permission p left join role r on p.rid=r.id left join user u on u.user_type=r.id where u.username=#{email}")
    String getPermission(String email);
}
