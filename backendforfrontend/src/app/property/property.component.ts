import {Component, Input, OnInit, Output} from '@angular/core';
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {Router} from "@angular/router";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {EventEmitter} from "events";
import {HttpClient} from "@angular/common/http";
import {PhotoComponent} from "../photo/photo.component";

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent implements OnInit {

  @Input("threadItem")
  threadItemString: ThreadItemComponent;
  @Output() gotoPropertyRequest = new EventEmitter<ThreadItemComponent>();
  image: any;
  imageReturn: PhotoComponent = new PhotoComponent();

  constructor(private router: Router,
              private threadService: ThreadItemServiceService)
  { }

  ngOnInit(): void {
    console.log(this.threadItemString.id, " is the thread item string to get photo");
    this.threadService.getPhoto(this.threadItemString.id)
      .subscribe(photoReturn => {
        console.log(photoReturn, " is the photo return");
        this.imageReturn = photoReturn;
        this.image = 'data:image/png;base64,'+this.imageReturn.binary;
      })
  }

}
