package com.app.thread.controller;

import com.app.thread.model.Photo;
import com.app.thread.service.PhotoService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class PhotoController {

    PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/uploadPhoto", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<Photo> uploadPhoto(@RequestPart("file") MultipartFile file) throws IOException {
        return photoService.savePhotoBytes(file.getBytes(), file.getOriginalFilename());
    }

    @PostMapping(value="/getPhotosList", consumes="application/json", produces="application/json")
    public Mono<List<Photo>> getPhotos(@RequestBody List<String> photos){
        return photoService.findPhotosById(photos).collectList();
    }

    @GetMapping("/getPhotoById/{photoId}")
    public Mono<Photo> uploadPhoto(@PathVariable String photoId){
        return photoService.findById(photoId);
    }

}
