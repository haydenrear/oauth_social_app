package com.app.thread.repo;

import com.app.thread.model.Post;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.app.thread.model.ThreadPost;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class TestRepo {

    @Autowired
    ThreadRepo threadRepo;
    @Autowired
    PostRepo postRepo;

    @Test
    public void testSaveThreadAndPost(){
        Post post = new Post("hello once again mister!");
        postRepo.save(post)
                .map(post1-> {
                    assertThat(post1.getId()).isNotNull();
                    return post1;
                })
                .subscribe();

        ThreadPost thread = new ThreadPost();

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

}
