package com.app.thread.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Post {

    @Id
    ObjectId id;
    String message;

    public Post(String message) {
        this.message = message;
    }
}
