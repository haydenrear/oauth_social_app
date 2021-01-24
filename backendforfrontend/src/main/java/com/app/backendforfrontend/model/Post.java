package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Post {

    String id;

    String message;

    public Post(String message) {
        this.message = message;
    }
}
