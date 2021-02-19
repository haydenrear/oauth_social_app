package com.app.backendforfrontend.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
public class AppEmailMessage {

  String text;
  String from;
  String to;
  File file;

}
