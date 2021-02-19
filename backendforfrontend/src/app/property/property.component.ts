import {Component, Input, OnInit, Output} from '@angular/core';
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {Router} from "@angular/router";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {EventEmitter} from "events";

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent implements OnInit {

  @Input("threadItem")
  threadItemString: ThreadItemComponent;
  @Output() gotoPropertyRequest = new EventEmitter<ThreadItemComponent>();

  constructor(private router: Router, private threadService: ThreadItemServiceService) { }

  threadItemId(){
    return this.threadItemString;
  }

  ngOnInit(): void {
    this.threadItemString.image = 'data:image/jpg;base64,'+this.threadItemString.image;
  }

}
