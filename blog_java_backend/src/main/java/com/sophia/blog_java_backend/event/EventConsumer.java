package com.sophia.blog_java_backend.event;

import com.alibaba.fastjson2.JSONObject;
import com.sophia.blog_java_backend.entity.DiscussPost;
import com.sophia.blog_java_backend.entity.Event;
import com.sophia.blog_java_backend.entity.Message;
import com.sophia.blog_java_backend.service.DiscussPostService;
import com.sophia.blog_java_backend.service.ElasticsearchService;
import com.sophia.blog_java_backend.service.MessageService;
import com.sophia.blog_java_backend.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventConsumer implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

//    @Autowired
//    private DiscussPostService discussPostService;
//
//    @Autowired
//    private ElasticsearchService elasticsearchService;

    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record){
        System.out.println("You are here 5");
        System.out.println(record.value());
        if(record == null || record.value() == null) {
            logger.error("消息的内容为空！");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        System.out.println("You are here 6");
        System.out.println(event == null);
        if (event == null) {
            logger.error("消息格式错误！");
            return;
        }

        System.out.println("You are here 8");
        // 发送站内通知
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());

        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());

        if(!event.getData().isEmpty()) {
            for(Map.Entry<String, Object> entry: event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.println("You are here 9");

        message.setContent(JSONObject.toJSONString(content));
        System.out.println("You are here 10");
        messageService.addMessage(message);
        System.out.println("You are here 7");
    }

    // 消费发帖事件
//    @KafkaListener(topics = {TOPIC_PUBLISH})
//    public void handlePublishMessage(ConsumerRecord record) {
//        if(record == null || record.value() == null) {
//            logger.error("消息的内容为空！");
//            return;
//        }
//        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
//        if (event == null) {
//            logger.error("消息格式错误！");
//            return;
//        }
//        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
//        elasticsearhService.saveDiscussPost(post);
//    }
}
