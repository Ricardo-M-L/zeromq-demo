package demo.fanout.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

import static demo.fanout.constant.RabbitmqConstant.FanoutQueueB;

/**
 * 消息接收监听类
 *
 * @author Ricardo.M.Lu
 */
@Component
@RabbitListener(queues = {FanoutQueueB})
public class FanoutReceiverB {
 
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("FanoutReceiverB消费者收到消息  : " +testMessage.toString());
    }
 
}