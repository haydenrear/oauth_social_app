import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/internal/operators";
import {BehaviorSubject, Observable, of} from "rxjs";
import {fromPromise} from "rxjs/internal-compatibility";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  notLoggedIn: boolean = true;

  loggedInBroad: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true)
  private coordinates: GeolocationCoordinates;

  constructor(private http: HttpClient) { }

  notLoggedInFunc(){
    this.http.get<boolean>("/isLoggedIn")
      .pipe(
        map(returnVal => {
          if(returnVal === true || returnVal === false){
            console.log(returnVal);
            this.notLoggedIn= returnVal;
            this.loggedInBroad.next(returnVal);
          }
          else {
            return true;
          }
        })
      )
      .subscribe();
    return this.notLoggedIn;
  }

  checkLocation() {
    return navigator.geolocation.getCurrentPosition;
  }

}
