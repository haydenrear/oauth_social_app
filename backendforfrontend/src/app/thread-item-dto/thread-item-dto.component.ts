import { Component, OnInit } from '@angular/core';
import {RegionComponent} from "../region/region.component";
import {PostComponent} from "../post/post.component";
import {ThreadItemDTO} from "../thread-item/thread-item.component";

@Component({
  selector: 'app-thread-item-dto',
  templateUrl: './thread-item-dto.component.html',
  styleUrls: ['./thread-item-dto.component.css']
})
export class ThreadItemDTOComponent implements OnInit, ThreadItemDTO {


  id: string;
  imageId: string;
  email: string;
  numBedrooms: number;
  numBathrooms: number;

  region: RegionComponent;
  posts: PostComponent [] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
