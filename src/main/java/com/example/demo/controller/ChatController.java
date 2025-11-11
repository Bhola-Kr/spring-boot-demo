package com.example.demo.controller;

import com.example.demo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") 
    @SendTo("/topic/public")       
    public ChatMessage sendMessage(ChatMessage message) {
        return new ChatMessage(message.getSender(), message.getContent(), "CHAT");
    }
}
