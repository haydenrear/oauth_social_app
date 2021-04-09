package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.Post;
import com.app.backendforfrontend.model.ThreadPost;
import com.app.backendforfrontend.threadservice.EmailService;
import com.app.backendforfrontend.threadservice.ThreadRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@Log4j2
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

  @PostMapping(value = "/newPost")
  public Mono<ThreadPost> addPostToThreadPost(@RequestBody ThreadPost threadItem,
                                              @AuthenticationPrincipal AuthenticatedPrincipal authenticatedPrincipal) {

    System.out.println(threadItem);

    Optional<Post> post= threadItem
      .getPosts()
      .stream()
      .filter(postFound -> postFound.getId() == null)
      .findFirst();

    if(post.isPresent() && emailService.sendMessage(post.get().getMessage(), authenticatedPrincipal.getName(), threadItem.getEmail(), null)){
      return threadRequestService.addPostToThreadPost(threadItem);
    }

    return Mono.empty();
  }

  @GetMapping("/findPropertiesByZipcode/{zipcode}")
  public Flux<ThreadPost> findThreadPostsByZipcode(@PathVariable(value = "zipcode") String zipcode){
    return threadRequestService.getThreadByZip(zipcode);
  }

  @GetMapping("/findPropertiesByLongitudeAndLatitude/{longitude}/{latitude}")
  public Flux<ThreadPost> findThreadPostsByLongAndLat(@PathVariable(value = "longitude") String longitude,@PathVariable(value = "longitude") String latitude){
    return threadRequestService.getThreadsByLongitudeAndLatitude(longitude, latitude);
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
    else if(filterBy.equalsIgnoreCase("cityState")){
      return threadRequestService.getThreadByCityState(filter);
    }
    return null;
  }



}
