import { Injectable } from '@angular/core';
import {ThreadItemComponent, ThreadItemDTO} from "./thread-item/thread-item.component";
import {HttpClient} from "@angular/common/http";
import {PhotoComponent} from "./photo/photo.component";
import {concatMap, map} from "rxjs/internal/operators";
import {from, Observable, of} from "rxjs";
import {PostComponent} from "./post/post.component";
import {ThreadItemDTOComponent} from "./thread-item-dto/thread-item-dto.component";
import {RegionComponent} from "./region/region.component";

@Injectable({
  providedIn: 'root'
})
export class ThreadItemServiceService {

  cache: boolean = false;

  constructor(private http: HttpClient) { }

  threadItemComponents: Map<string, ThreadItemDTO> = new Map<string, ThreadItemDTO>();
  photos: Map<string, PhotoComponent> = new Map<string, PhotoComponent>();
  threadIds: string [] = [];
  photoToThread: Map<string, string> = new Map<string, string>();
  threadToPhoto: Map<string, string> = new Map<string, string>();

  addThread(threadToAdd: ThreadItemDTO): void {
    if(!this.photoToThread.has(threadToAdd.id)){
      let threadId = threadToAdd.id;
      let imageId = threadToAdd.imageId;
      console.log(imageId, " is image id in add thread thread item service");
      this.threadItemComponents.set(threadToAdd.id, threadToAdd);
      this.photoToThread.set(threadId, imageId);
      this.threadToPhoto.set(imageId, threadId);
    }
  }

  addPost(threadId: string, postComponent: PostComponent): void{
    this.getThread(threadId).posts.push(postComponent);
  }

  getThread(threadId: string): ThreadItemDTO {
    return this
      .threadItemComponents
      .get(threadId);
  }

  newThread(): ThreadItemDTO {
    let threadItem = new ThreadItemDTOComponent();
    let region = new RegionComponent();
    threadItem.region = region;
    return threadItem;
  }

  addThreads(item: ThreadItemDTO[]): void {
    item.forEach(threadItem => {
      this.addThread(threadItem);
    });
  }

  addPhoto(photo: PhotoComponent): boolean {
    if(!this.photos.has(photo.id)){
      this.photos.set(photo.id, photo);
      return true;
    }
    return false;
  }

  getPhoto(threadId: string): Observable<PhotoComponent>{
    let photoid = this.photoToThread.get(threadId);
    console.log(photoid, " is photo id");
    if(this.photos.has(photoid)){
      console.log("photos has photoid");
      return of(this.photos.get(photoid));
    }
    else if(this.photos.size === 0){
      console.log("photos is size zero ...");
      return this.getPhotos(this.photoToThread)
        .pipe(
          map(photoArray => {
            return photoArray.filter((photo) => {
              return photoid === photo.id;
            })[0]
          })
        );
    }
    else {
      console.log("calling get photo by id with", photoid);
      return this.http.get<PhotoComponent>("/getPhotoById/"+photoid)
        .pipe(
          map(photoReturn => {
            if(this.addPhoto(photoReturn)){
              this.photoToThread.set(threadId, photoReturn.id);
              this.threadToPhoto.set(photoReturn.id, threadId);
            }
            return photoReturn;
          })
        )
    }
  }

  getPhotos(photoIds: Map<string, string>): Observable<PhotoComponent []>{
    if(this.photos.size !== 0){
      return of(Array.from(this.photos.values()));
    }
    console.log(photoIds.values(), " is the values");
    let photoIdsArray = Array.from(photoIds.values());
    let headers = {};
    headers['Content-Type'] = 'application/json';
    headers['Accept'] = 'application/json';
    return this.http.post<PhotoComponent []>("/getPhotos", JSON.stringify(photoIdsArray), {headers: headers})
      .pipe(
        map((photoArray) => {
          photoArray.forEach(photo => {
            if (this.addPhoto(photo)) {
              let threadId = this.threadToPhoto.get(photo.id);
              this.photoToThread.set(threadId, photo.id);
              this.threadToPhoto.set(photo.id, threadId);
            }
          })
          return photoArray;
        })
      );
  };

  getThreads(): Observable<ThreadItemDTO []> {
    if(this.threadToPhoto.size !== 0){
      return of(Array.from(this.threadItemComponents.values()));
    }
    return this.http.get<ThreadItemDTO []>("/threadPost")
      .pipe(
        map(item => {
          this.addThreads(item);
          console.log(item);
          return Array.from(this.threadItemComponents.values());
        })
      )
  }

  filterThreads(city: string, filterBy: string, filter: string): Observable<ThreadItemDTO []> {
    if(city == null || city == "" || filterBy == "zipcode"){
      return this.http.get<ThreadItemDTO []>("/filterBy/"+filter+"/"+filterBy)
        .pipe(
          map(item => {
            this.addThreads(item);
            console.log(item);
            return item;
          }));
    }
    let cityState = encodeURI("/filterBy/"+city+", "+filter+"/cityState");
    return this.http.get<ThreadItemDTO []>(cityState)
      .pipe(
        map(item => {
          this.addThreads(item);
          console.log(item);
          return item;
        }));
  }

  // getCache(): Observable<boolean> {
  //   return this.getNumberThreads()
  //     .pipe(
  //       map(someNumber => someNumber !== this.threadItemComponents.size)
  //     );
  // }
  //
  // getNumberThreads(): Observable<number>{
  //   return this.http.get<number>("/numThreads");
  // }


  getThreadsWithLongLat(longitude: number, latitude: number): Observable<ThreadItemDTO[]> {
    if(this.threadToPhoto.size !== 0){
      return of(Array.from(this.threadItemComponents.values()));
    }
    return this.http.get<ThreadItemDTO []>("/threadPost")
      .pipe(
        map(item => {
          this.addThreads(item);
          console.log(item);
          return Array.from(this.threadItemComponents.values());
        })
      )
  }
}
