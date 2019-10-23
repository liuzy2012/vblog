package com.vi3nty.blog.service;

import com.vi3nty.blog.entity.Message;

import java.util.List;

/**
 * @author : vi3nty
 * @date : 19:54 2019/10/22
 */
public interface IMessageService {
    int insertMessage(Message message);
    int updateStatus(List<Integer> ids,int status);
    Message selectLastNotice(int userId,String topic);
    int selectNoticeCount(int userId,String topic);
    int selectNoticeUnreadedCount(int userId,String topic);
    List<Message> allUnreadedNotice(int userId,String topic);
}
