package com.app.thread.config;

import com.app.thread.model.Photo;
import com.app.thread.model.Post;
import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.PostRepo;
import com.app.thread.repo.RegionRepo;
import com.app.thread.service.PhotoService;
import com.app.thread.service.RegionService;
import com.app.thread.service.ThreadService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class InitDatabase implements ApplicationContextAware {


    ApplicationContext ctx;
    RegionService regionService;
    PhotoService photoService;
    ThreadService threadService;

    public InitDatabase(RegionService regionService, PhotoService photoService, ThreadService threadService) {
        this.regionService = regionService;
        this.photoService = photoService;
        this.threadService = threadService;
    }

    @Bean
    public CommandLineRunner initBean(PostRepo postRepo) {
        return command -> {
            Flux.range(1, 10)
                    .map(intValue -> new Post("hello" + intValue))
                    .flatMap(postRepo::save)
                    .flatMap(post -> getPhoto()
                            .map(photo -> threadPost(post, photo))
                            .flatMap(threadService::addThread)
                            .map(this::saveRegion)
                    ).subscribe();
        };
    }

    @Autowired
    public void setRegionService(RegionService regionService){
        this.regionService = regionService;
    }

    @Autowired
    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public ThreadPost saveRegion(ThreadPost threadPost){
        Region innerRegion = threadPost.getRegion();
        innerRegion.setThreadPostId(threadPost.getId());
        regionService.saveRegionWithPoint(innerRegion).subscribe();
        return threadPost;
    }

    public Mono<Photo> getPhoto(){
        try {
            File file = new File("thread/src/main/resources/shiba.jpg");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = fileInputStream.readAllBytes();
            Photo shiba = new Photo(bytes, "jpg");
            return photoService.savePhoto(shiba);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    public ThreadPost threadPost(Post post, Photo photo){
        List<Post> postList = List.of(post, post, post, post);
        Region region = ctx.getBean(Region.class, "97219", "2085 oakmont way, Eugene OR", "Oregon", "eugene");
        return new ThreadPost(postList, photo.getId(), region, "***REMOVED***.rear@gmail.com", 1, 1);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
