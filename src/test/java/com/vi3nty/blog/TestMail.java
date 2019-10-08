package com.vi3nty.blog;

import com.vi3nty.blog.utils.MailClient;
import com.vi3nty.blog.utils.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : vi3nty
 * @date : 21:19 2019/9/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = BlogApplication.class)
public class TestMail {

    @Autowired
    private MailClient mailClient;

    @Test
    public void testMail(){
        mailClient.sendMail("liuzhaoyang94@163.com","123","内容222222222");
    }
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Test
    public void testSensitive(){
        System.out.println(sensitiveFilter.filter("我今晚去操你妈"));
    }

}
