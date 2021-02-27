package com.app.backendforfrontend;

import com.app.backendforfrontend.controller.PhotoController;
import com.app.backendforfrontend.model.Photo;
import com.app.backendforfrontend.threadservice.PhotoService;
import jdk.dynalink.linker.support.Guards;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BackendforfrontendApplicationTests {


  @Autowired
  PhotoService photoService;
  @Autowired
  PhotoController photoController;

  @Test
  void contextLoads() {
  }

  @Test
  void testSendingPhotoServiceRequest(){
    Iterable<String> photoIds = List.of("60326e73a628bf74d2d117b7","60326e73a628bf74d2d117b7","60326e73a628bf74d2d117b7");
//    Photo photo = photoService.getPhotos((List<String>) photoIds)
////    .blockFirst();
//    assertThat(photo).isNotNull();
  }

  @Test
  public void testRequest(){
    Iterable<String> photoIds = List.of("60326e73a628bf74d2d117b7");
    String [] strings = new String[2];
    strings[0] = "60326e73a628bf74d2d117b7";
    strings[1] = "60326e73a628bf74d2d117b7";
//    Photo photo = photoController.getPhotos(List.of(strings))
//      .blockFirst();
//    assertThat(photo).isNotNull();
  }


}
