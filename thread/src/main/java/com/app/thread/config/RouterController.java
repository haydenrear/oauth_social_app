package com.app.thread.config;

import com.app.thread.model.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterController {

//    @Bean
//    RouterFunction<?> routerFunction(){
//        return route(GET("/hello"),
//                request -> ok().body(Mono.just(new Post("Hello")), Post.class));
//    }
}
