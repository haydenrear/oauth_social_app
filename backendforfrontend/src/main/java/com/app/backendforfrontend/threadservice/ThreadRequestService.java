package com.app.backendforfrontend.threadservice;

import com.app.backendforfrontend.model.ThreadPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
      .body(zipcode, String.class)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToFlux(ThreadPost.class);
  }
}
