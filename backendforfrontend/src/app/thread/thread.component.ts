import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {map} from "rxjs/internal/operators";
import {Scheduler} from "rxjs/internal/Rx";
import {UserService} from "../user.service";

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {

  threadItem: ThreadItemComponent [] = [];
  filterStuff: any;
  filter: string;
  filterBy: string;

  filterByFunc(){
    console.log(this.filter+this.filterBy);
    this.http.get<ThreadItemComponent []>("/filterBy/"+this.filter+"/"+this.filterBy)
      .subscribe(thread => this.threadItem = thread);
  }

  constructor(private http: HttpClient, private threadService: ThreadItemServiceService, private userService: UserService) { }

  ngOnInit(): void {
    this.http.get<ThreadItemComponent []>("/threadPost")
      .subscribe(item => {
       this.threadItem = item;
       this.threadService.addThreads(item);
       console.log(item);
    });
  }
}
