package org.example.queuepractice.consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.queuepractice.common.dto.MessageDto;
import org.example.queuepractice.common.parser.JsonMessageParser;
import org.example.queuepractice.handler.DynamicHandlerInvoker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final JsonMessageParser jsonMessageParser;
    private final DynamicHandlerInvoker handlerInvoker;

    @JmsListener(destination = "${spring.activemq.destination}")
    public void receiveMessage(MessageDto messageDto) {
        String type = messageDto.getType();
        String handlerClassName = type + "Handler";
        log.info("Receive message from queue: {}", handlerClassName);

        handlerInvoker.invoke(handlerClassName, messageDto.getMessage());
    }
}