package com.app.thread.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPost {

    @Id
    ObjectId id;

    List<Post> posts;

    String name;
    byte [] image;

    Region region;
    String email;

    public ThreadPost(List<Post> posts, String name, byte[] image, Region region, String email) {
        this.posts = posts;
        this.name = name;
        this.image = image;
        this.region = region;
        this.email= email;
    }
}
