package com.app.thread.controller;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.app.thread.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
public class ThreadController {

    ThreadService threadService;

    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/allThreads")
    public Flux<ThreadPost> threadPost(){
        log.info("sending threadposts!");
        return threadService.findAllThreads();
    }

    @PostMapping("/addThread")
    public Mono<ThreadPost> addThread(@RequestBody ThreadPost threadPost){
        return threadService.addThread(threadPost);
    }

    @PostMapping("/findThreadByZip")
    public Flux<ThreadPost> findThreadByZip(@RequestBody String zipcode){
        return threadService.findThreadByZip(zipcode);
    }

}
