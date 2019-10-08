package com.vi3nty.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author : vi3nty
 * @date : 21:09 2019/9/24
 * 邮件发送工具类
 */
@Component
public class MailClient {
    private static final Logger LOGGER=LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailfrom;

    /**
     *
     * @param mailTo 收件人
     * @param subject 邮件标题
     * @param content 邮件正文
     */
    public void sendMail(String mailTo,String subject,String content){
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(mailfrom);
            helper.setTo(mailTo);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            LOGGER.error("发送邮件失败"+e.getMessage());
        }

    }
}
