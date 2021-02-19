package com.app.thread.repo;

import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ThreadRepo extends ReactiveMongoRepository<ThreadPost, String> {
    Flux<ThreadPost> findAllByRegion(Region region);
    Mono<ThreadPost> findByNameIs(String name);
}