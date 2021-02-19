package com.app.backendforfrontend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
@Scope("prototype")
public class ThreadPost {

  String id;
  List<Post> posts;

  String name;
  byte [] image;

  Region region;
  String email;

  int numBedrooms;
  int numBathrooms;


}
