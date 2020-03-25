//package com.vi3nty.blog.event;
//
//import com.alibaba.fastjson.JSONObject;
//import com.vi3nty.blog.entity.Event;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * kafka生产者
// * @author : vi3nty
// * @date : 20:51 2019/10/16
// */
//@Component
//public class EventProducer {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    //处理事件
//    public void fireEvent(Event event){
//
//        //将事件发布到指定的主题topic
//        kafkaTemplate.send(event.getTopic(),JSONObject.toJSONString(event));
//    }
//}
