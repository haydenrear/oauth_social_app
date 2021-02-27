package com.app.thread.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Document
@Component
public class Post {

    @Id
    String id;
    String message;
    String fromEmail;

    public Post(String message) {
        this.message = message;
    }
}
