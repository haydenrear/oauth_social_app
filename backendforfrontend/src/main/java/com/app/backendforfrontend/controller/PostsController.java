package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.ThreadPost;
import com.app.backendforfrontend.threadservice.ThreadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PostsController {

    private final ThreadRequestService threadRequestService;

    @Autowired
    public PostsController(ThreadRequestService threadRequestService) {
        this.threadRequestService = threadRequestService;
    }

    @GetMapping("/threadPost")
    public Mono<ThreadPost> threadPost(){
        return threadRequestService.getThread();
    }

}
