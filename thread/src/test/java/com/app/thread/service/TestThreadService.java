package com.app.thread.service;

import com.app.thread.model.Post;
import com.app.thread.model.ThreadPost;
import com.netflix.discovery.converters.Auto;
import com.netflix.discovery.shared.Pair;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class TestThreadService {

    @Autowired
    ThreadService threadService;

    @Test
    public void testThreadServiceSavesThreadPost(){
        ThreadPost threadPost = new ThreadPost();
        Post post = new Post();
        threadPost.setPosts(List.of(post));
        ThreadPost threadPostRet = threadService
                .addThread(threadPost)
                .block();
        threadService.addPost(new Pair<>(threadPostRet, post))
                .subscribe(log::info);
    }

}
