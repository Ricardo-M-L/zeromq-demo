package fz.controller;

import fz.constant.PubSubConstant;
import fz.pubsub.ZmqPubClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@RestController
@RequestMapping("/pub")
public class PubController {

    @Value("${zeromq.send-address}")
    private String sendAddress;

    @SneakyThrows
    @GetMapping("/send/message")
    public void sendMessage() {
        ZmqPubClient zmqPubClient = new ZmqPubClient(sendAddress);
        for (int i = 0; i < 100; i++) {
            String weatherMessage = PubSubConstant.TOPIC_WEATHER + "_" + System.currentTimeMillis() + "_" + (i + 1) + "\t";
            zmqPubClient.sendData(PubSubConstant.TOPIC_WEATHER.getBytes(), weatherMessage.getBytes());
            log.info("\u001B[32m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_WEATHER + "]主题上发送消息：" + weatherMessage + " 给服务端" + "\u001B[0m");

            String newsMessage = PubSubConstant.TOPIC_NEWS + "_" + System.currentTimeMillis() + "_" + (i + 1) + "\t";
            zmqPubClient.sendData(PubSubConstant.TOPIC_NEWS.getBytes(), newsMessage.getBytes());
            log.info("\u001B[34m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_NEWS + "]主题上发送消息：" + newsMessage + " 给服务端" + "\u001B[0m" + "\n");

            Thread.sleep(1000);
        }
        zmqPubClient.close();
    }

}