package com.vi3nty.blog.event;

import com.alibaba.fastjson.JSONObject;
import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.utils.Constant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author : vi3nty
 * @date : 20:56 2019/10/16
 */
@Component
public class EventConsumer implements Constant {

    private static final Logger LOGGER=LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private ICommentService iCommentService;

    @KafkaListener(topics = {TOPIC_COMMENT})
    public void handleCommentMeaage(ConsumerRecord record){
        if(record==null||record.value()==null){
            LOGGER.error("消息内容为空");
            return;
        }
        Event event=JSONObject.parseObject(record.value().toString(),Event.class);
        if(event==null){
            LOGGER.error("消息格式错误");
            return;
        }
    }
}
