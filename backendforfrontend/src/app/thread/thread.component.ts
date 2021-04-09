import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemDTO} from "../thread-item/thread-item.component";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {UserService} from "../user.service";

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

  constructor(private http: HttpClient, private threadService: ThreadItemServiceService, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.checkLocation()(onSuccess => {
      //Todo: add this call
      this.threadService.getThreadsWithLongLat(onSuccess.coords.longitude, onSuccess.coords.latitude)
        .subscribe(threads => {
          this.threadItem = threads;
        });
    }, onFailure => console.log(onFailure));
  }
}
