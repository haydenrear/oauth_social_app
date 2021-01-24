package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ThreadPost {

    String id;
    List<Post> posts;

    public ThreadPost(List<Post> posts) {
        this.posts = posts;
    }
}
