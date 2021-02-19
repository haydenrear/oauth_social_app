import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {RegionComponent} from "../region/region.component";
import {StateConverterService} from "../state-converter.service";

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
  streetModel: string;
  streetNumberModel: string;

  constructor(private http: HttpClient, private stateConvert: StateConverterService) { }

  ngOnInit(): void {
  }

  submit() {
    console.log(this.zipcodeModel);
    this.region.zip = this.zipcodeModel;
    this.region.address = this.streetNumberModel+" "+this.streetModel+" "+this.stateModel+", "+this.stateModel;
    this.region.state = this.stateConvert.convertRegion(this.stateModel, this.stateConvert.TO_NAME);
    this.threadItem.region = this.region;
    this.http.post<ThreadItemComponent>("/newProperty", this.threadItem)
      .subscribe(returnThreadItemComponent => this.threadItem.id = returnThreadItemComponent.id);
  }

}
