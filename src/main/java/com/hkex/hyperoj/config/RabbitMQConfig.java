package com.hkex.hyperoj.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    /**
     * 后端 -> 沙箱 判题请求队列
     */
    public static final String CODE_EXECUTE_PRODUCER_QUEUE = "code.execute.producer";

    /**
     * 沙箱 -> 后端 判题结果队列
     */
    public static final String CODE_EXECUTE_CONSUMER_QUEUE = "code.execute.consumer";

    /**
     * 代码执行交换机（direct）
     */
    public static final String CODE_EXECUTE_EXCHANGE = "code.event.exchange";

    /**
     * 发送判题请求的路由键
     */
    public static final String CODE_EXECUTE_ROUTING_KEY_SUBMIT = "code.execute.submit";

    @Bean
    public Queue codeExecuteProducerQueue(){
        return QueueBuilder.durable(CODE_EXECUTE_PRODUCER_QUEUE).build();
    }

    @Bean
    public DirectExchange codeExecuteExchange(){
        return new DirectExchange(CODE_EXECUTE_EXCHANGE);
    }

    @Bean
    public Binding codeExecuteProducerBinding(Queue codeExecuteProducerQueue, DirectExchange codeExecuteExchange) {
        return BindingBuilder.bind(codeExecuteProducerQueue).to(codeExecuteExchange).with(CODE_EXECUTE_ROUTING_KEY_SUBMIT);
    }

    /**
     * 结果队列由沙箱直接发送到队列（可通过默认交换机），这里仅声明队列，是否绑定由沙箱端决定
     */
    @Bean
    public Queue codeExecuteConsumerQueue(){
        return QueueBuilder.durable(CODE_EXECUTE_CONSUMER_QUEUE).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
