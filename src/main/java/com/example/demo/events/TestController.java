package com.example.demo.events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestProducer producer;

    public TestController(TestProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/send")
    public String send(@RequestParam(defaultValue = "Hello from Spring Boot") String msg) {
        producer.sendMessage(msg);
        return "Message sent: " + msg;
    }
}
