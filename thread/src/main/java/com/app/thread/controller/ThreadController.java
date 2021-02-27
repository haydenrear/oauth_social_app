package com.app.thread.controller;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.app.thread.service.ThreadService;
import com.netflix.discovery.shared.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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

    @PostMapping("/addPostToThread")
    public Mono<ThreadPost> addPostToThread(@RequestBody ThreadPost threadPost){
        Optional<Post> postFound =  threadPost
                .getPosts()
                .stream()
                .filter(post -> post.getId() == null)
                .findFirst();
        if(postFound.isPresent()){
            return threadService.addPost(threadPost, postFound.get());
        }
        else {
            return Mono.empty();
        }
    }

    @PostMapping("/findThreadByZip")
    public Flux<ThreadPost> findThreadByZip(@RequestBody String zipcode){
        return threadService.findThreadByZip(zipcode);
    }

    @PostMapping("/findThreadByState")
    public Flux<ThreadPost> findThreadByState(@RequestBody String state){
        return threadService.findThreadByState(state);
    }

    @PostMapping("/findByCityState")
    public Flux<ThreadPost> findByCityState(@RequestBody String cityState){
        return threadService.findThreadByAddress(cityState);
    }

    @PostMapping("/findThreadByAddress")
    public Flux<ThreadPost> findThreadByAddress(@RequestBody String address){
        return threadService.findThreadByAddress(address);
    }

    @GetMapping("/threadById{id}")
    public Mono<ThreadPost> findThreadById(@PathVariable("id") String id){
        return threadService.findThreadById(id);
    }

}
