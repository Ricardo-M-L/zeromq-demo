package demo.topic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static demo.topic.constant.RabbitmqConstant.*;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@RestController("topicSendMessageController")
@RequestMapping("/topic")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "this is my first topic message!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(TopicExchange,FirstTopicRouting, map);
        log.info("生产者发送消息: {}到了" + TopicExchange + "交换机", map.toString());
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2(){
        String messageId = UUID.randomUUID().toString();
        String messageData = "this is my second topic message!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(TopicExchange,SecondTopicRouting, map);
        log.info("生产者发送消息: {}到了" + TopicExchange + "交换机", map.toString());
        return "ok";
    }


}
