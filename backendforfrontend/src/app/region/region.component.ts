import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-region',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent implements OnInit {

  constructor(private http: HttpClient) { }

  zip: string;
  address: string;
  state: string;
  region: string;

  ngOnInit(): void {
  }

}
