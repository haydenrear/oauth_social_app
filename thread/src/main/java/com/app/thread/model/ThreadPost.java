package com.app.thread.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope("prototype")
public class ThreadPost {

    @Id
    String id;

    List<Post> posts;

    String name;
    byte [] image;

    Region region;
    String email;
    int numBedrooms;
    int numBathrooms;

    public ThreadPost(List<Post> posts, String name, byte[] image, Region region, String email, int numBedrooms, int numBathrooms) {
        this.posts = posts;
        this.name = name;
        this.image = image;
        this.region = region;
        this.email = email;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
    }
}
