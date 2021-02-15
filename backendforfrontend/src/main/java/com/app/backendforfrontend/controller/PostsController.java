package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.ThreadPost;
import com.app.backendforfrontend.threadservice.ThreadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PostsController {

  private final ThreadRequestService threadRequestService;

  @Autowired
  public PostsController(ThreadRequestService threadRequestService) {
      this.threadRequestService = threadRequestService;
  }

  @GetMapping("/threadPost")
  public Flux<ThreadPost> threadPost(@AuthenticationPrincipal AuthenticatedPrincipal principal){
    System.out.println(principal);
    return threadRequestService.getThread();
  }

  @PostMapping("/newProperty")
  public Mono<ThreadPost> newThreadPost(@RequestBody ThreadPost threadPost, @AuthenticationPrincipal AuthenticatedPrincipal principal){
    String email = principal.getName();
    threadPost.setEmail(email);
    return threadRequestService.sendThread(threadPost);
  }

  @GetMapping("/findPropertiesByZipcode/{zipcode}")
  public Flux<ThreadPost> findThreadPostsByZipcode(@PathVariable(value = "zipcode") String zipcode){
    return threadRequestService.getThreadByZip(zipcode);
  }



}
