package com.app.backendforfrontend.threadservice;

import com.app.backendforfrontend.model.Post;
import com.app.backendforfrontend.model.ThreadPost;
import com.netflix.discovery.shared.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class ThreadRequestService {

    WebClient client;

    @Value("${url.threadpost.url}")
    String threadpostUrl;

    public ThreadRequestService(WebClient client) {
        this.client = client;
    }

    public Flux<ThreadPost> getThread(){
        return client
                .get().uri(threadpostUrl+"/allThreads")
                .attributes(clientRegistrationId("google"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ThreadPost.class);
    }

  public Mono<ThreadPost> sendThread(ThreadPost threadPost) {
    return client
      .post()
      .uri(threadpostUrl+"/addThread")
      .attributes(clientRegistrationId("google"))
      .bodyValue(threadPost)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(ThreadPost.class);
  }

  public Flux<ThreadPost> getThreadByZip(String zipcode) {
    return client
      .post()
      .uri(threadpostUrl+"/findThreadByZip")
      .attributes(clientRegistrationId("google"))
      .bodyValue(zipcode)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToFlux(ThreadPost.class);
  }

  public Flux<ThreadPost> getThreadByState(String state) {
    return client
      .post()
      .uri(threadpostUrl+"/findThreadByState")
      .attributes(clientRegistrationId("google"))
      .bodyValue(state)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToFlux(ThreadPost.class);
  }

  public Mono<ThreadPost> findThreadById(String id) {
    return null;
  }

    public Mono<ThreadPost> addPostToThreadPost(ThreadPost threadPost, Post post) {
      Pair<ThreadPost, Post> threadPostPostPair = new Pair<>(threadPost, post);
      return client
        .post()
        .uri(threadpostUrl+"/addPostToThread")
        .bodyValue(threadPostPostPair)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ThreadPost.class);
    }
}
