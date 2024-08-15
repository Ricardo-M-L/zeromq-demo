package demo.pubsub;

import demo.constant.PubSubConstant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pub {
    public static void main(String[] args) throws InterruptedException {
        ZmqPubClient zmqPubClient = new ZmqPubClient("tcp://*:7777");
        for (int i = 0; i < 100;i++){
            String weatherMessage = PubSubConstant.TOPIC_WEATHER+ "_" + System.currentTimeMillis()+"_"+(i+1) + "\t";
            zmqPubClient.sendData(PubSubConstant.TOPIC_WEATHER.getBytes(),weatherMessage.getBytes());
            log.info("\u001B[32m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_WEATHER + "]主题上发送消息：" + weatherMessage + " 给服务端" + "\u001B[0m");

            String newsMessage = PubSubConstant.TOPIC_NEWS+ "_" + System.currentTimeMillis()+"_"+(i+1) + "\t";
            zmqPubClient.sendData(PubSubConstant.TOPIC_NEWS.getBytes(),newsMessage.getBytes());
            log.info("\u001B[34m" + "java客户端第" + (i + 1) + "通过发布订阅模式在[" + PubSubConstant.TOPIC_NEWS + "]主题上发送消息：" + newsMessage + " 给服务端" + "\u001B[0m"+"\n");

            Thread.sleep(1000);
        }
        zmqPubClient.close();
    }
}