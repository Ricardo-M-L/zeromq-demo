package fz.pubsub;

import fz.constant.PubSubConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Slf4j
@Component
public class Sub implements ApplicationRunner {

    @Value("${zeromq.receive-address}")
    private String receiveAddress;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ZmqSubThread zmqSubThread = new ZmqSubThread(receiveAddress, PubSubConstant.TOPIC_ALL) {
            @Override
            public void dealWithReceive(byte[] topicBytes, byte[] contentBytes) {
                log.info("\u001B[32m" + "java服务端通过发布订阅模式接收到[ " + new String(topicBytes, ZMQ.CHARSET) + "]主题的消息为："
                        + new String(contentBytes, ZMQ.CHARSET) + "\u001B[0m");
            }
        };

        Thread thread = new Thread(zmqSubThread);
        thread.start();
    }
}