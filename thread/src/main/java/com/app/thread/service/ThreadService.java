package com.app.thread.service;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.PostRepo;
import com.app.thread.repo.ThreadRepo;
import com.netflix.discovery.shared.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ThreadService {

    ThreadRepo threadRepo;
    RegionService regionService;
    PostRepo postRepo;

    public ThreadService(ThreadRepo threadRepo, RegionService regionService, PostRepo postRepo) {
        this.threadRepo = threadRepo;
        this.regionService = regionService;
        this.postRepo = postRepo;
    }

    public Flux<ThreadPost> findAllThreads(){
        return threadRepo.findAll();
    }

    public Mono<ThreadPost> addThread(ThreadPost threadPost) {
        return threadRepo.save(threadPost);
    }

    public Flux<ThreadPost> findThreadByZip(String zipcode) {
        return regionService.findRegionsNearAny(zipcode)
                .flatMap(region -> threadRepo.findById(region.getThreadPostId().toString()));
    }

    public Flux<ThreadPost> findThreadByState(String state) {
        return regionService.findRegionsNearAny(state)
                .flatMap(region -> threadRepo.findById(region.getThreadPostId().toString()));
    }

    public Flux<ThreadPost> findThreadByAddress(String address) {
        return regionService.findRegionsNearAny(address)
                .flatMap(region -> threadRepo.findById(region.getThreadPostId().toString()));
    }

    public Mono<ThreadPost> addPost(Pair<ThreadPost, Post> threadPost) {
        return postRepo.save(threadPost.second())
            .map(postRet -> {
                threadPost.second().setId(postRet.getId());
                return threadPost.first();
            }).flatMap(threadRepo::save);
    }

}
