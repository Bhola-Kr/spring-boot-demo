package com.example.demo.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public String sendNotification(String token, String title, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification sent successfully: " + response);
            return response;

        } catch (FirebaseMessagingException e) {
            System.err.println("❌ FirebaseMessagingException: " + e.getMessage());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Cause: " + e.getCause());
            e.printStackTrace();
            return "❌ Firebase error: " + e.getErrorCode() + " - " + e.getMessage();
        }

    }
}
