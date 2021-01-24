package com.app.thread.repo;

import com.app.thread.model.ThreadPost;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepo extends ReactiveMongoRepository<ThreadPost, String> {
}
