package org.example.queuepractice.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.example.queuepractice.common.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
@Slf4j
public class ActiveMqConfig {

    @Value("${spring.activemq.broker-url}")
    private String activemqBrokerUrl;

    @Value("${spring.activemq.user}")
    private String activemqUsername;

    @Value("${spring.activemq.password}")
    private String activemqPassword;

    @Value("${spring.activemq.destination}")
    private String destination;

    // Queue 정의
    @Bean
    public Queue queue() {
        return new ActiveMQQueue(destination);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(activemqBrokerUrl);
        activeMQConnectionFactory.setUserName(activemqUsername);
        activeMQConnectionFactory.setPassword(activemqPassword);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setExplicitQosEnabled(true);    // 메시지 전송 시 QOS을 설정
        jmsTemplate.setDeliveryPersistent(false);   // 메시지의 영속성을 설정
        jmsTemplate.setReceiveTimeout(1000 * 3);    // 메시지를 수신하는 동안의 대기 시간을 설정(3초)
        jmsTemplate.setTimeToLive(1000 * 60 * 30);  // 메시지의 유효 기간을 설정(30분)
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("message", MessageDto.class);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }
}
