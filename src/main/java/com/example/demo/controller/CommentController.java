package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CommentResponse;
import com.example.demo.service.CommentService;

@RestController
@RequestMapping("/external")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/{postId}")
    public CommentResponse[] getComments(@PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }
    
}
