import {Component, OnInit} from '@angular/core';
import {PostComponent} from "../post/post.component";
import {RegionComponent} from "../region/region.component";
import {ActivatedRoute, Router} from "@angular/router";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {HttpClient} from "@angular/common/http";

export interface ThreadItemDTO {

  id: string;
  imageId: string;
  email: string;
  numBedrooms: number;
  numBathrooms: number;

  region: RegionComponent;
  posts: PostComponent [];

}

@Component({
  selector: 'app-thread-item',
  templateUrl: './thread-item.component.html',
  styleUrls: ['./thread-item.component.css']
})
export class ThreadItemComponent implements OnInit, ThreadItemDTO {

  id: string;
  imageId: string;
  email: string;
  numBedrooms: number;
  numBathrooms: number;
  post: PostComponent = new PostComponent();

  region: RegionComponent;
  posts: PostComponent [] = [];
  threadItem: ThreadItemDTO;
  image: any;

  constructor(private threadService: ThreadItemServiceService,
              private http: HttpClient,
              private activatedRoute: ActivatedRoute,
              private router: Router
              ) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;
    if(this.id !== undefined){
      this.threadItem = this.threadService.getThread(this.id);
    }
    console.log(this.threadItem.imageId, " is the image id");
    this.threadService.getPhoto(this.threadItem.id)
      .subscribe(photoReturn => {
        this.image = 'data:image/png;base64,'+photoReturn.binary;
      });
  }

  addMessage() {
    this.threadItem.posts.push(this.post);
    this.http.post<PostComponent>("/newPost", this.threadItem)
      .subscribe(postReturn => {
        this.posts.push(postReturn);
        this.threadService.addPost(this.id, postReturn);
        this.router.navigate(["/thread"])
      });
  }

}
