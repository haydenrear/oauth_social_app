package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Post {

    ObjectId id;

    String message;

    public Post(String message) {
        this.message = message;
    }
}
