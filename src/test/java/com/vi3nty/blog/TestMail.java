package com.vi3nty.blog;

import com.vi3nty.blog.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : vi3nty
 * @date : 21:19 2019/9/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlogApplication.class)
public class TestMail {

    @Autowired
    private IMessageService service;

    @Test
    public void changeMsgStatus(){
        List<Integer> ids=new LinkedList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        service.updateStatus(ids,1);
    }

    @Test
    public void max(){
        System.out.println(service.selectNoticeCount(10,"comment"));
        System.out.println(service.selectNoticeUnreadedCount(10,"comment"));
    }
}
