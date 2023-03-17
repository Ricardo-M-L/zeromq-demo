package demo.listener;

import demo.config.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息接收监听类
 * @author Ricardo.M.Lu
 */
@Slf4j
@Component
@RabbitListener(queues = {RabbitmqConstant.DirectQueue})  //监听的消息队列名称
public class SecondDirectReceiver {

    //@RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用,也可以直接放在方法上使用
    @RabbitHandler
    public void receiveMessage(Map message) {
        log.info(RabbitmqConstant.SecondDirectReceiver+ "消费者收到了消息 ：" +message.toString());
    }
}
