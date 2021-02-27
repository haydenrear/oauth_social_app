package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.Photo;
import com.app.backendforfrontend.threadservice.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.List;

@RestController
public class PhotoController {


  public PhotoController(PhotoService photoService) {
    this.photoService = photoService;
  }

  PhotoService photoService;

  @PostMapping("/uploadPhoto")
  public Mono<Photo> uploadPhoto(@RequestPart("file") FilePart binary){
    System.out.println(binary);
    return photoService.sendPhoto(binary);
  }

  @GetMapping("/getPhotoById/{photoId}")
  public Mono<Photo> getPhotoById(@PathVariable String photoId){
    return photoService.getPhotoById(photoId);
  }

  @PostMapping(value = "/getPhotos", consumes="application/json", produces="application/json")
  public Mono<List<Photo>> getPhotos(@RequestBody List<String> photoIds){
    System.out.println(photoIds);
    return photoService.getPhotos(photoIds);
  }


}
