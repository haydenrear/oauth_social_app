package com.app.thread.service;

import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.ThreadRepo;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ThreadService {

    public ThreadService(ThreadRepo threadRepo) {
        this.threadRepo = threadRepo;
    }

    ThreadRepo threadRepo;

    public Flux<ThreadPost> findAllThreads(){
        return threadRepo.findAll();
    }

    public Mono<ThreadPost> addThread(ThreadPost threadPost) {
        return threadRepo.save(threadPost);
    }

    public Flux<ThreadPost> findThreadByZip(String zipcode) {
//       return threadRepo.findAll(Example
//               .of(new ThreadPost(null, null, null, new Region(zipcode, null, null, null), null))
//       );
        return null;
    }
}
