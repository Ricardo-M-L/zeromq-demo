package demo.simple.pubsub;


import demo.constant.PubSubConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

/**
 * @author Ricardo.M.Lu
 * 这个类实现了Spring Boot的ApplicationRunner接口，用于在应用启动时执行ZeroMQ的订阅者逻辑。
 */
@Slf4j
public class Subscriber {

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

        subscriber.connect("tcp://localhost:5555");
        // 订阅 "weather" 主题
        subscriber.subscribe(PubSubConstant.TOPIC_WEATHER.getBytes(ZMQ.CHARSET));
        // 订阅 所有主题
        //subscriber.subscribe(PubSubConstant.TOPIC_ALL.getBytes(ZMQ.CHARSET));

        while (!Thread.currentThread().isInterrupted()) {
            // 接收消息（主题 + 内容）  0：表示使用默认行为接收消息
            byte[] topicBytes = subscriber.recv(0);
            byte[] contentBytes = subscriber.recv(0);

            log.info("\u001B[32m" + "java服务端通过发布订阅模式接收到[ " + new String(topicBytes, ZMQ.CHARSET) + "]主题的消息为："
                    + new String(contentBytes, ZMQ.CHARSET) + "\u001B[0m");
        }
    }
}