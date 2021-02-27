import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemDTO} from "../thread-item/thread-item.component";
import {ThreadItemServiceService} from "../thread-item-service.service";

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {

  threadItem: ThreadItemDTO [] = [];
  filterStuff: any;
  filter: string;
  filterBy: string;
  city: any;

  filterByFunc(){
    this.threadService.filterThreads(this.city, this.filterBy, this.filter)
      .subscribe(threads => {
        this.threadItem = threads;
      });
  }

  constructor(private http: HttpClient, private threadService: ThreadItemServiceService) { }

  ngOnInit(): void {
    this.threadService.getThreads()
      .subscribe(threads => {
        this.threadItem = threads;
      });
  }
}
