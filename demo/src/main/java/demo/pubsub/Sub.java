package demo.pubsub;

import demo.constant.PubSubConstant;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

@Slf4j
public class Sub {
    public static void main(String[] args) {

        ZmqSubThread zmqSubThread = new ZmqSubThread("tcp://localhost:7777",PubSubConstant.TOPIC_ALL) {
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