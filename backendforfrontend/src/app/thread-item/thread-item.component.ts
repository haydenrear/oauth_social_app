import {Component, InjectionToken, Injector, OnInit} from '@angular/core';
import {PostComponent} from "../post/post.component";
import {RegionComponent} from "../region/region.component";
import {ActivatedRoute} from "@angular/router";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {HttpClient} from "@angular/common/http";
import {AppEmailMessageComponent} from "../app-email-message/app-email-message.component";

@Component({
  selector: 'app-thread-item',
  templateUrl: './thread-item.component.html',
  styleUrls: ['./thread-item.component.css']
})
export class ThreadItemComponent implements OnInit {

  id: string;
  name: string;
  image: any;
  email: string;
  numBedrooms: number;
  numBathrooms: number;

  region: RegionComponent;
  posts: PostComponent [];
  emailMessage: AppEmailMessageComponent;

  constructor(private threadService: ThreadItemServiceService,
              private http: HttpClient,
              private activatedRoute: ActivatedRoute,
              ) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;

    const threadItem: ThreadItemComponent = this.threadService.getThread(this.id)[0];
    this.name = threadItem.name;
    this.image = threadItem.image;
    this.email = threadItem.email;
    this.region = threadItem.region;
    this.posts= threadItem.posts;
    this.numBathrooms = threadItem.numBathrooms;
    this.numBedrooms = threadItem.numBedrooms;
  }

  addMessage() {

    this.emailMessage.to = this.email;
    this.emailMessage.file = null;

    let post = new PostComponent();
    post.message = this.emailMessage.text;
    this.posts.push(post);

    this.http.post<PostComponent>("/newPost", { threadPost: this, message: this.emailMessage})
      .subscribe(postReturn => {
        this.posts.push(postReturn);
      });

  }

}
