package com.app.backendforfrontend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
public class ThreadPost {

  ObjectId id;
  List<Post> posts;

  String name;
  byte [] image;

  Region region;
  String email;

  public ThreadPost( ObjectId id,  List<Post> posts,  String name,  byte[] image,  Region region, String emaill) {
    this.id = id;
    this.posts = posts;
    this.name = name;
    this.image = image;
    this.region = region;
    this.email = email;
  }
}
