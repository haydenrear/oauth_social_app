import { Component, OnInit } from '@angular/core';
import {PostComponent} from "../post/post.component";
import {RegionComponent} from "../region/region.component";

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

  region: RegionComponent;
  posts: PostComponent [];

  constructor() { }

  ngOnInit(): void {
  }

}
