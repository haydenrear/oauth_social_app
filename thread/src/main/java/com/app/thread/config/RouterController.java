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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterController {



    @Bean
    public CommandLineRunner initBean(PostRepo postRepo,
                                      ThreadService threadService,
                                      PhotoService photoService,
                                      RegionService regionService,
                                      ApplicationContext ctx) throws IOException {
        InputStream inputStream = new FileInputStream(new ClassPathResource("shiba.jpg").getFile());
        Photo shiba = ctx.getBean(Photo.class, inputStream.readAllBytes(), ".jpg");
        Photo photo = photoService.savePhoto(shiba).block();
        return command -> {
            IntStream.range(1, 10)
                    .mapToObj(intValue -> new Post("hello"+intValue))
                    .map(postRepo::save)
                    .forEach(postMono -> postMono.subscribe(post -> {
                        List<Post> postList = List.of(post, post, post, post);
                        Region region = ctx.getBean(Region.class, "97219", "2085 oakmont way, Eugene OR", "Oregon", "eugene");
                        ThreadPost threadPost = new ThreadPost(postList, photo.getId(), region, "***REMOVED***.rear@gmail.com", 1, 1);
                        threadService.addThread(threadPost)
                                .map(threadPost1 -> {
                                    Region innerRegion = threadPost1.getRegion();
                                    innerRegion.setThreadPostId(threadPost1.getId());
                                    regionService.saveRegionWithPoint(innerRegion).subscribe();
                                    return threadPost1;
                                }).subscribe();
                    }));
        };
    }
}
