import {Component, Inject, Injector, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ThreadItemComponent, ThreadItemDTO} from "../thread-item/thread-item.component";
import {RegionComponent} from "../region/region.component";
import {StateConverterService} from "../state-converter.service";
import {Router} from "@angular/router";
import {PhotoComponent} from "../photo/photo.component";
import {File} from "@angular/compiler-cli/src/ngtsc/file_system/testing/src/mock_file_system";
import {concatMap, concatMapTo, flatMap, map} from "rxjs/internal/operators";
import {concat, from, Observable, of} from "rxjs";
import {Scheduler} from "rxjs/internal/Rx";
import {ThreadItemServiceService} from "../thread-item-service.service";
import {fromPromise} from "rxjs/internal-compatibility";
import {FileChangeEvent} from "@angular/compiler-cli/src/perform_watch";

@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.css']
})
export class PropertyFormComponent implements OnInit {

  imageUpload: any;

  stateModel: string = "";
  streetModel: string = "";
  streetNumberModel: string = "";
  formData: FormData = new FormData();
  threadItem: ThreadItemComponent;
  threadItemForm: ThreadItemDTO;
  imageType: string;

  constructor(private http: HttpClient,
              private stateConvert: StateConverterService,
              private threadItemService: ThreadItemServiceService,
              private router: Router)
  {}

  ngOnInit(): void {
    this.threadItemForm = this.threadItemService.newThread();
  }

  submit() {

    this.threadItemForm.region.address =
      this.streetNumberModel+" "+this.streetModel+" "
      +this.threadItemForm.region.city+", "+this.stateModel;

    this.threadItemForm.region.state = this.stateConvert.convertRegion(this.stateModel, this.stateConvert.TO_NAME);

    this.savePhoto()
      .pipe(
        concatMap(returnPhoto => {
          console.log(returnPhoto.id);
          console.log(returnPhoto);
          this.threadItemForm.imageId = returnPhoto.id;
          console.log(this.threadItemForm);
          this.threadItemService.addPhoto(returnPhoto);
          return of(this.threadItemForm);
        }),
        concatMap(returnThread => {
          return this.http.post<ThreadItemComponent>("/newProperty", returnThread);
        })
      ).subscribe(threadItemReturned => {
      this.threadItem = threadItemReturned;
      this.threadItemService.addThread(this.threadItem);
      this.router.navigate(["/thread"])
    });

  };

  savePhoto(): Observable<PhotoComponent>{
    return this.http.post<PhotoComponent>("/uploadPhoto", this.formData)
  }

  preparePhoto(event: Event){
    const target = event.target as HTMLInputElement;
    let file = (target.files as FileList)[0]
    console.log(file);
    console.log(this.formData);
    this.formData.set("file", file);
    console.log(this.formData);
  }
}
