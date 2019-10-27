package com.vi3nty.blog.event;

import com.alibaba.fastjson.JSONObject;
import com.vi3nty.blog.entity.Article;
import com.vi3nty.blog.entity.Comment;
import com.vi3nty.blog.entity.Event;
import com.vi3nty.blog.entity.Message;
import com.vi3nty.blog.service.IArticleService;
import com.vi3nty.blog.service.ICommentService;
import com.vi3nty.blog.service.IMessageService;
import com.vi3nty.blog.service.impl.ElasticsearchService;
import com.vi3nty.blog.utils.Constant;
import com.vi3nty.blog.utils.HtmlParse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : vi3nty
 * @date : 20:56 2019/10/16
 */
@Component
public class EventConsumer implements Constant {

    private static final Logger LOGGER=LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private ElasticsearchService elasticsearchService;
    @KafkaListener(topics = {TOPIC_COMMENT,TOPIC_LIKE})
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
        //发送站内通知
        Message message=new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        //设置当前通知种类
        message.setType(event.getTopic());
        Map<String,Object> map=new HashMap<>();
        map.put("userId",event.getUserId());
        map.put("entityType",event.getEntityType());
        map.put("entityId",event.getEntityId());
        if(!event.getData().isEmpty()){
            for(Map.Entry<String,Object> entry:event.getData().entrySet()){
                map.put(entry.getKey(),entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(map));
        iMessageService.insertMessage(message);

    }

    //消费发帖事件，存入es服务器
    @KafkaListener(topics = TOPIC_PUBLISH)
    public void handlePublishMessage(ConsumerRecord record){
        if(record==null||record.value()==null){
            LOGGER.error("消息内容为空");
            return;
        }
        Event event=JSONObject.parseObject(record.value().toString(),Event.class);
        if(event==null){
            LOGGER.error("消息格式错误");
            return;
        }

        Article post=iArticleService.getArticleById(event.getEntityId());
        post.setContent(HtmlParse.markToHtml(post.getContent()));
        elasticsearchService.saveArticlePost(post);

    }
}
