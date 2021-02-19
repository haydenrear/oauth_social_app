import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "../login/login.component";
import {ThreadComponent} from "../thread/thread.component";
import {PropertyFormComponent} from "../property-form/property-form.component";
import {PropertyComponent} from "../property/property.component";
import {ThreadItemComponent} from "../thread-item/thread-item.component";


const routes: Routes = [
  {path: 'thread', component: ThreadComponent},
  {path: 'login', component: LoginComponent},
  {path: 'propertyForm', component: PropertyFormComponent},
  {path: 'threadItem/:id', component: ThreadItemComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class RoutingRoutingModule {
}
