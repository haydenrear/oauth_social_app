package com.app.thread.repo;

import com.app.thread.model.Photo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PhotoRepo extends ReactiveMongoRepository<Photo, String> {
    Flux<Photo> findById(Iterable<String> photos);
}
