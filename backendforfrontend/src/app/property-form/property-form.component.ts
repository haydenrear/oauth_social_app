import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {RegionComponent} from "../region/region.component";

@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.css']
})
export class PropertyFormComponent implements OnInit {

  numBedrooms: number;
  numBathrooms: number;
  image: any;
  zipcodeModel: string;
  stateModel: string;

  threadItem: ThreadItemComponent;
  region: RegionComponent;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  submit() {
    console.log(this.zipcodeModel);
    this.region.zip = this.zipcodeModel;
    this.region.address = "";
    this.region.state = this.stateModel;
    this.threadItem.region = this.region;
    this.http.post("/newProperty", this.threadItem);
  }
}
