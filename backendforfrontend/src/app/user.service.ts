import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/internal/operators";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  notLoggedIn: boolean;

  loggedInBroad: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true)

  constructor(private http: HttpClient) { }

  notLoggedInFunc(){
    this.http.get<boolean>("/isLoggedIn")
      .pipe(
        map(returnVal => {
          console.log(returnVal);
          this.notLoggedIn= returnVal;
          this.loggedInBroad.next(returnVal);
          return returnVal;
        })
      )
      .subscribe();
    return this.notLoggedIn;
  }

}
