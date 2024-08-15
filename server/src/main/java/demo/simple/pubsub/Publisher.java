package demo.simple.pubsub;

import demo.constant.PubSubConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
public class Publisher  {

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://localhost:5555");

        for (int i = 0; i < 100; i++) {
            // 假设我们有两个主题："weather" 和 "news"

            //消息内容
            String weatherMessage = "It's sunny today.";
            String newsMessage = "Big news today!";

            // 发送关于天气的消息
            publisher.send(PubSubConstant.TOPIC_WEATHER.getBytes(), ZMQ.SNDMORE);
            publisher.send(weatherMessage.getBytes(), ZMQ.NOBLOCK);
            log.info("\u001B[32m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_WEATHER + "]主题上发送消息：" + weatherMessage + " 给服务端" + "\u001B[0m");

            // 发送关于新闻的消息
            publisher.send(PubSubConstant.TOPIC_NEWS.getBytes(), ZMQ.SNDMORE);
            publisher.send(newsMessage.getBytes(), ZMQ.NOBLOCK);
            log.info("\u001B[34m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_NEWS+ "]主题上发送消息：" + newsMessage + " 给服务端" + "\u001B[0m"+"\n" );

            try {
                Thread.sleep(1000); // 等待一秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        publisher.close();
        context.term();
    }

}