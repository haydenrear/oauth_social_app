import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-app-email-message',
  templateUrl: './app-email-message.component.html',
  styleUrls: ['./app-email-message.component.css']
})
export class AppEmailMessageComponent implements OnInit {

  text: string;
  from: string;
  to: string;
  file: File;

  constructor() { }

  ngOnInit(): void {
  }

}
