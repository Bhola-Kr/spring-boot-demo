package com.example.demo.controller;

import com.example.demo.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String title = body.get("title");
        String message = body.get("message");

        String result = notificationService.sendNotification(token, title, message);
        return Map.of("status", "ok", "result", result);
    }
}
