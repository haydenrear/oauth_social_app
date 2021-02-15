import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MatTabChangeEvent} from "@angular/material/tabs";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {


  constructor(private router: Router) {
    this.router = router;
  }

  goToProperties($event: MatTabChangeEvent) {
    console.log("hi");
    console.log($event.tab.textLabel);
    let url: string = this.getRouterUrl($event.tab.textLabel);
    this.router.navigate([url]);
  }

  getRouterUrl(tabLabel: string): string{
    if(tabLabel == "View Properties"){
      return "thread";
    }
    else if (tabLabel == "Add Property"){
      return "propertyForm";
    }
    else if(tabLabel == "Login"){
      return "login";
    }
  }

  ngOnInit(): void{
    this.router.navigate(["thread"]);
  }
}
