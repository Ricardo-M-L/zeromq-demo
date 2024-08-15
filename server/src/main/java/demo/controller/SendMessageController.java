//package demo.direct.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import static demo.direct.constant.RabbitmqConstant.DirectExchange;
//import static demo.direct.constant.RabbitmqConstant.DirectRouting;
//
///**
// * @author Ricardo.M.Lu
// */
//@Slf4j
//@RestController("directSendMessageController")
//@RequestMapping("/direct")
//public class SendMessageController {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法
//
//    @GetMapping("/sendDirectMessage")
//    public String sendDirectMessage() {
//        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "this is my direct messgae!";
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String, Object> map = new HashMap<>();
//        map.put("messageId", messageId);
//        map.put("messageData", messageData);
//        map.put("createTime", createTime);
//        //将消息携带绑定键值：DirectRouting 发送到交换机DirectExchange
//        rabbitTemplate.convertAndSend(DirectExchange, DirectRouting, map);
//        log.info("生产者发送消息: {}到了" + DirectExchange + "交换机", map.toString());
//        return "ok";
//    }
//
//}