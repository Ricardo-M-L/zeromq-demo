package demo.fanout.controller;

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

import static demo.fanout.constant.RabbitmqConstant.FanoutExchange;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@RestController("fanoutSendMessageController")
@RequestMapping("/fanout")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "this is my fanout messgae!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息发送到交换机DirectExchange
        rabbitTemplate.convertAndSend(FanoutExchange, null, map);
        log.info("生产者发送消息: {}到了" + FanoutExchange + "交换机", map.toString());
        return "ok";
    }

}