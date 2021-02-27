package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@Scope("prototype")
public class Post {

    String id;
    String message;
    String fromEmail;

    public Post(String message) {
        this.message = message;
    }
}
