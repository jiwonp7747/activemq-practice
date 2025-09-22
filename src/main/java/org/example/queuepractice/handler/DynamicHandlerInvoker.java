package org.example.queuepractice.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class DynamicHandlerInvoker {

    private final ApplicationContext context;

    public void invoke(String className, String message) {
        try {
            // 클래스 로드
            Class<?> clazz = Class.forName("org.example.queuepractice.handler." + className);
            // 스프링 빈 가져오기
            Object bean = context.getBean(clazz);
            // handle(String message) 실행
            Method method = clazz.getMethod("handle", String.class);
            method.invoke(bean, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
