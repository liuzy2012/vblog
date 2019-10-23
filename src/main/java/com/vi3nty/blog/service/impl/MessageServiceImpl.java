package com.vi3nty.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vi3nty.blog.entity.Message;
import com.vi3nty.blog.mapper.MessageMapper;
import com.vi3nty.blog.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 19:55 2019/10/22
 */
@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public int insertMessage(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public int updateStatus(List<Integer> ids, int status) {
        return messageMapper.updateStatus(status,ids);
    }

    /**
     * 查询最近的通知
     * @param userId
     * @param topic
     * @return
     */
    @Override
    public Message selectLastNotice(int userId, String topic) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("to_id",userId);
        wrapper.eq("type",topic);
        wrapper.eq("from_id",1);
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        return messageMapper.selectOne(wrapper);
    }

    @Override
    public int selectNoticeCount(int userId, String topic) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("to_id",userId);
        wrapper.eq("type",topic);
        return messageMapper.selectCount(wrapper);
    }

    @Override
    public int selectNoticeUnreadedCount(int userId, String topic) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("to_id",userId);
        wrapper.eq("type",topic);
        wrapper.eq("status",0);
        return messageMapper.selectCount(wrapper);
    }

    @Override
    public List<Message> allUnreadedNotice(int userId, String topic) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("to_id",userId);
        wrapper.eq("type",topic);
        wrapper.eq("status",0);
        return messageMapper.selectList(wrapper);
    }

}
