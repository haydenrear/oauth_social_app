package com.app.thread.repo;

import com.app.thread.model.Post;
import com.app.thread.model.Region;
import com.app.thread.service.RegionService;
import com.mongodb.client.model.geojson.Position;
import com.netflix.discovery.converters.Auto;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.app.thread.model.ThreadPost;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class TestRepo {

    @Autowired
    ThreadRepo threadRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    RegionRepo regionRepo;

    ThreadPost thread;
    Post post;

    @Autowired
    RegionService regionService;
    Region region;


    @BeforeEach
    public void BeforeEach(){
        region =  new Region("97219", "2085 Oakmont Way", "OR", "Eugene");
        regionRepo.save(region).subscribe();
    }

    @BeforeEach
    public void beforeEach(){
        if(thread != null){
            threadRepo.delete(thread);
            postRepo.delete(post);
        }
        post = new Post("hello once again mister!");
        postRepo.save(post)
                .map(post1-> {
                    assertThat(post1.getId()).isNotNull();
                    return post1;
                })
                .subscribe();

        thread = new ThreadPost();
    }

    @Test
    public void testSaveThreadAndPost(){

        threadRepo.save(thread)
                .flatMap(threadNoPost -> {
                    threadNoPost.setPosts(List.of(post));
                    return threadRepo.save(threadNoPost);
                })
                .map(thread1 -> {
                    assertThat(thread1.getPosts().size()).isNotZero();
                    log.info(thread1.getPosts().get(0)+": is the thread post");
                    return thread1;
                })
                .subscribe();
    }

    @Test
    public void testRegionRepoFindNear(){
        Mono<GeoJsonPolygon> poly = regionService.setPointZip(region);
        poly.map(polygon -> regionRepo.findByLocationIsWithin(polygon).subscribe(System.out::println))
                .subscribe(System.out::println);
    }



}
