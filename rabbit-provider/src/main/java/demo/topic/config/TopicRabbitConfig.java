package demo.topic.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static demo.topic.constant.RabbitmqConstant.*;

/**
 * 主题交换机
 * @author Ricardo.M.Lu
 */

@Configuration
public class TopicRabbitConfig {

    @Bean
    TopicExchange topicEXchange() {
        return new TopicExchange(TopicExchange);
    }

    @Bean
    public Queue firstTopicQueue() {
        return new Queue(FirstTopicQueue);
    }

    @Bean
    public Queue secondTopicQueue() {
        return new Queue(SecondTopicQueue);
    }

    //将FirstTopicQueue和TopicExchange绑定,而且绑定的键值为topic.first
    //这样只要是消息携带的路由键是topic.first,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage1() {
        return BindingBuilder.bind(firstTopicQueue()).to(topicEXchange()).with(FirstTopicRouting);
    }

    //将SecondTopicQueue和TopicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondTopicQueue()).to(topicEXchange()).with("topic.#");
    }
}