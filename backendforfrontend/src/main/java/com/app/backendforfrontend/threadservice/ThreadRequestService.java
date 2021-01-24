package com.app.backendforfrontend.threadservice;

import com.app.backendforfrontend.model.ThreadPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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

    public Mono<ThreadPost> getThread(){
        return client
                .get().uri(threadpostUrl+"/hello")
                .attributes(clientRegistrationId("google"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ThreadPost.class);
    }



}
