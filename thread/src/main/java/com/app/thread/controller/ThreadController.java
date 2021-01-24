package com.app.thread.controller;

import com.app.thread.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ThreadController {

    @GetMapping("/hello")
    public Mono<Post> post(){
        return Mono.just(new Post("hello"));
    }

}
