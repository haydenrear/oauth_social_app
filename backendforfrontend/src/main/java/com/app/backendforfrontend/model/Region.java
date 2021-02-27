package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@Scope("prototype")
public class Region {

  String id;
  String threadPostId;
  String zip;
  String address;
  String state;
  String city;

}
