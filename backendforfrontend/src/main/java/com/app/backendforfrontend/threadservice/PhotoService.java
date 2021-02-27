package com.app.backendforfrontend.threadservice;

import com.app.backendforfrontend.model.Photo;
import com.app.backendforfrontend.model.ThreadPost;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class PhotoService implements ApplicationContextAware {

  WebClient client;

  @Value("${url.threadpost.url}")
  String threadpostUrl;
  private ApplicationContext applicationContext;

  public PhotoService(WebClient client) {
    this.client = client;
  }

  public Mono<Photo> sendPhoto(Photo photo) {
    return client
      .post()
      .uri(threadpostUrl+"/uploadPhoto")
      .attributes(clientRegistrationId("google"))
      .bodyValue(photo)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Photo.class);
  }

  public Mono<Photo> sendPhoto(FilePart file) {
    return client
      .post()
      .uri(threadpostUrl+"/uploadPhoto")
      .attributes(clientRegistrationId("google"))
      .body(BodyInserters.fromMultipartData("file",file))
      .retrieve()
      .bodyToMono(Photo.class);
  }

  public Mono<Photo> getPhotoById(String photoId) {
    return client
      .get()
      .uri(threadpostUrl+"/getPhotoById/"+photoId)
      .attributes(clientRegistrationId("google"))
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(Photo.class);
  }

  public Mono<List<Photo>> getPhotos(List<String> photoIds) {
    return client
      .post()
      .uri(threadpostUrl+"/getPhotosList")
      .attributes(clientRegistrationId("google"))
      .bodyValue(photoIds)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(new ParameterizedTypeReference<List<Photo>>() {});
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
