package com.app.thread.config;

import com.app.thread.model.Post;
import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.PostRepo;
import com.app.thread.repo.RegionRepo;
import com.app.thread.repo.ThreadRepo;
import com.app.thread.service.RegionService;
import com.app.thread.service.ThreadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterController {



    @Bean
    public CommandLineRunner initBean(PostRepo postRepo, ThreadService threadService, RegionRepo regionRepo, RegionService regionService) throws IOException {
        InputStream inputStream = new FileInputStream(new ClassPathResource("shiba.jpg").getFile());
        byte [] shiba = inputStream.readAllBytes();
        return command -> {
            IntStream.range(1, 10)
                    .mapToObj(intValue -> new Post("hello"+intValue))
                    .map(postRepo::save)
                    .forEach(postMono -> postMono.subscribe(post -> {
                        List<Post> postList = List.of(post, post, post, post);
                        Region region = new Region(null, null, "97219", "2085 okay", "OR", "midwest", null);
                        ThreadPost threadPost = new ThreadPost(postList, "newPost", shiba, region, "someEmail@email.com");
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
