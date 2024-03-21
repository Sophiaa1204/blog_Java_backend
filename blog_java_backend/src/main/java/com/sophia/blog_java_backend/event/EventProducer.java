package com.sophia.blog_java_backend.event;

import com.alibaba.fastjson2.JSONObject;
import com.sophia.blog_java_backend.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        System.out.println("You are here 3");
        // 将事件发送到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
        System.out.println("You are here 4");
    }
}
