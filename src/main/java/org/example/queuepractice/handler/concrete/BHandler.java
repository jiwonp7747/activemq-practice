package org.example.queuepractice.handler.concrete;

import org.example.queuepractice.handler.template.AbstractHandler;
import org.springframework.stereotype.Service;

@Service
public class BHandler extends AbstractHandler {

    @Override
    protected void processMessage(String message) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected String getHandlerName() {
        return "B";
    }
}