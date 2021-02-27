package com.app.thread.service;

import com.app.thread.model.Photo;
import com.app.thread.repo.PhotoRepo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PhotoService implements ApplicationContextAware {

    PhotoRepo photoRepo;
    ApplicationContext applicationContext;

    public PhotoService(PhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }

    public Mono<Photo> savePhoto(Photo photo){
        return photoRepo.save(photo);
    }

    public Mono<Photo> savePhoto(FilePart filePart){
        Photo photo = applicationContext.getBean(Photo.class, filePart, filePart.filename().split(".")[1]);
        return photoRepo.save(photo);
    }

    public Flux<Photo> findPhotosById(Iterable<String> photos) {
        return photoRepo.findById(photos);
    }

    public Mono<Photo> findById(String photoId) {
        return photoRepo.findById(photoId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Mono<Photo> savePhotoBytes(byte[] bytes, String filename) {
        Photo photo = applicationContext.getBean(Photo.class, bytes, filename);
        return photoRepo.save(photo);
    }
}
