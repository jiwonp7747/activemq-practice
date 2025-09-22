package org.example.queuepractice.handler.template;

import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public abstract class AbstractHandler {

    @Async
    public void handle(String message) {
        String handlerName = getHandlerName();

        // Processing 로그 (Template Method)
        logProcessing(handlerName, message);

        // 실제 처리 로직 (구현 클래스에서 정의)
        processMessage(message);

        // Finished 로그 (Template Method)
        logFinished(handlerName);
    }

    // 하위 클래스에서 구현해야 하는 추상 메서드
    protected abstract void processMessage(String message);
    protected abstract String getHandlerName();

    // Template Method: Processing 로그
    private void logProcessing(String handlerName, String message) {
        System.out.println("Processing " + handlerName + " in background: " + message +
                          " start time: " + LocalDateTime.now());
    }

    // Template Method: Finished 로그
    private void logFinished(String handlerName) {
        System.out.println("Finished " + handlerName + " end time: " + LocalDateTime.now());
    }
}