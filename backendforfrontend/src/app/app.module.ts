import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSliderModule } from '@angular/material/slider';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenuComponent } from './menu/menu.component';
import { RouterModule } from "@angular/router";
import { MatTabsModule } from '@angular/material/tabs'
import {RoutingRoutingModule} from "./routing/routing.module";
import { ThreadComponent } from './thread/thread.component';
import { PropertyComponent } from './property/property.component';
import { PropertyFormComponent } from './property-form/property-form.component';
import {MatCardModule} from '@angular/material/card';
import { ThreadItemComponent } from './thread-item/thread-item.component';
import {HttpClientModule} from "@angular/common/http";
import { PostComponent } from './post/post.component';
import { RegionComponent } from './region/region.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    ThreadComponent,
    PropertyComponent,
    PropertyFormComponent,
    TabDirective,
    ThreadItemComponent,
    PostComponent,
    RegionComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSliderModule,
    RouterModule,
    MatTabsModule,
    RoutingRoutingModule,
    MatCardModule,
    HttpClientModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
