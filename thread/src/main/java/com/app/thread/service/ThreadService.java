package com.app.thread.service;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.PostRepo;
import com.app.thread.repo.ThreadRepo;
import com.netflix.discovery.shared.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        return threadRepo.save(threadPost)
                .map(threadSaved -> {
                    threadSaved.getRegion().setThreadPostId(threadSaved.getId());
                    regionService.saveRegionWithPoint(threadSaved.getRegion()).subscribe();
                    return threadSaved;
                });
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

    public Mono<ThreadPost> addPost(ThreadPost threadPost, Post post) {
        return postRepo.save(post)
            .map(postRet -> {
                threadPost.setId(postRet.getId());
                return threadPost;
            }).flatMap(threadRepo::save);
    }

    public Mono<ThreadPost> findThreadById(String id) {
        return threadRepo.findById(id);
    }
}
