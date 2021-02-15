import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemComponent} from "../thread-item/thread-item.component";

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {

  threadItem: ThreadItemComponent [] = [];

  filterByZip(zip: string){
    this.http.post<ThreadItemComponent []>("/byZipCode", zip)
      .subscribe(thread => this.threadItem = thread);
  }

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<ThreadItemComponent []>("/threadPost")
      .subscribe(thread => this.threadItem = thread);
    console.log(this.threadItem);
  }
}
