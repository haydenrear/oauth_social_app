package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.AppEmailMessage;
import com.app.backendforfrontend.model.Post;
import com.app.backendforfrontend.model.ThreadPost;
import com.app.backendforfrontend.threadservice.EmailService;
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
  EmailService emailService;

  public PostsController(ThreadRequestService threadRequestService, EmailService emailService) {
    this.threadRequestService = threadRequestService;
    this.emailService = emailService;
  }

  @GetMapping("/threadPost")
  public Flux<ThreadPost> threadPost(@AuthenticationPrincipal AuthenticatedPrincipal principal){
    System.out.println(principal);
    return threadRequestService.getThread();
  }

  @GetMapping("/isLoggedIn")
  public boolean isLoggedIn(@AuthenticationPrincipal AuthenticatedPrincipal authenticatedPrincipal){
    return authenticatedPrincipal==null;
  }

  @GetMapping("/threadById/{threadId}")
  public Mono<ThreadPost> threadPost(@PathVariable(value = "threadId") String id){
    return threadRequestService.findThreadById(id);
  }

  @PostMapping("/newProperty")
  public Mono<ThreadPost> newThreadPost(@RequestBody ThreadPost threadPost, @AuthenticationPrincipal AuthenticatedPrincipal principal){
    String email = principal.getName();
    threadPost.setEmail(email);
    return threadRequestService.sendThread(threadPost);
  }

  @PostMapping("/newPost")
  public Mono<ThreadPost> addPostToThreadPost(@RequestBody ThreadPost threadPost,
                                              AppEmailMessage message,
                                              @RequestBody Post post,
                                              @AuthenticationPrincipal AuthenticatedPrincipal principal){
    message.setFrom(principal.getName());
    if(emailService.sendMessage(message)){
      return threadRequestService.addPostToThreadPost(threadPost, post);
    }
    return Mono.empty();
  }

  @GetMapping("/findPropertiesByZipcode/{zipcode}")
  public Flux<ThreadPost> findThreadPostsByZipcode(@PathVariable(value = "zipcode") String zipcode){
    return threadRequestService.getThreadByZip(zipcode);
  }

  @GetMapping("/filterBy/{filter}/{filterBy}")
  public Flux<ThreadPost> findThreadPosts(@PathVariable(value="filter") String filter,
                                          @PathVariable(value="filterBy") String filterBy){
    if(filterBy.equalsIgnoreCase("zipcode")){
      return threadRequestService.getThreadByZip(filter);
    }
    else if(filterBy.equalsIgnoreCase("state")){
      return threadRequestService.getThreadByState(filter);
    }
    return null;
  }



}
