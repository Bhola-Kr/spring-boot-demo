package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CommentResponse;

@Service
public class CommentService {

    @Autowired
    private RestTemplate restTemplate;

    public CommentResponse[] getCommentsByPostId(int postId) {
        String url = "https://jsonplaceholder.typicode.com/comments?postId=" + postId;

        return restTemplate.getForObject(url, CommentResponse[].class);
    }
    
}
