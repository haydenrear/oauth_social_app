import { Injectable } from '@angular/core';
import {ThreadItemComponent} from "./thread-item/thread-item.component";

@Injectable({
  providedIn: 'root'
})
export class ThreadItemServiceService {

  constructor() { }

  threadItemComponents: ThreadItemComponent [] = [];
  threadIds: string [] = [];

  addThread(threadToAdd: ThreadItemComponent){
    if(!this.threadIds.includes(threadToAdd.id)){
      this.threadItemComponents.push(threadToAdd);
    }
  }

  getThread(threadId: string){
    return this.threadItemComponents.filter(threadItem =>threadItem.id === threadId);
  }

  addThreads(item: ThreadItemComponent[]) {
    item.forEach(threadItem => {
      this.addThread(threadItem);
      console.log(threadItem);
    });
  }
}
