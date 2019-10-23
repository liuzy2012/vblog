package com.vi3nty.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vi3nty.blog.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 19:54 2019/10/22
 */
public interface MessageMapper extends BaseMapper<Message> {

    //修改消息状态
    int updateStatus(@Param("status") int status,@Param("ids") List<Integer> ids);

    //查询某个主题下最新的通知
    Message selectLateestNotice(@Param("userId") int userId,@Param("topic") String topic);

    //查询某个主题所包含的通知数量

    //查询未读的通知的数量

}
