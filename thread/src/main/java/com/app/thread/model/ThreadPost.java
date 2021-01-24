package com.app.thread.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
public class ThreadPost {

    @Id
    ObjectId id;

    @DBRef
    List<Post> posts;

    public ThreadPost(List<Post> posts) {
        this.posts = posts;
    }
}
