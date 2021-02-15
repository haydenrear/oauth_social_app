import {Component, Input, OnInit} from '@angular/core';
import {ThreadItemComponent} from "../thread-item/thread-item.component";

@Component({
  selector: 'app-property',
  templateUrl: './property.component.html',
  styleUrls: ['./property.component.css']
})
export class PropertyComponent implements OnInit {

  @Input("threadItem")
  threadItemString: ThreadItemComponent;

  constructor() { }

  ngOnInit(): void {
    this.threadItemString.image = 'data:image/jpg;base64,'+this.threadItemString.image;
  }



}
