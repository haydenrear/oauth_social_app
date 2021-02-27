import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyFormComponent } from './property-form.component';
import {ThreadItemComponent} from "../thread-item/thread-item.component";
import {By} from "@angular/platform-browser";
import {DebugElement} from "@angular/core";
import {element} from "protractor";

describe('PropertyFormComponent', () => {
  let component: PropertyFormComponent;
  let fixture: ComponentFixture<PropertyFormComponent>;
  let threadItemFixture: ComponentFixture<ThreadItemComponent>;
  let threadItemComponentInst: ThreadItemComponent;
  let rootElement: DebugElement= null;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyFormComponent, ThreadItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    rootElement= fixture.debugElement;
  });

  beforeEach(() => {
    threadItemFixture = TestBed.createComponent(ThreadItemComponent);
    threadItemComponentInst = threadItemFixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(threadItemComponentInst).toBeTruthy();
  });

  it('should send post thread item component', () => {
    let numBedrooms= element(By.css('.numBedrooms'));
    let numBathrooms= element(By.css('.numBathrooms'));
    let streetNumber= element(By.css('.streetNumber'));
    let street= element(By.css('.street'));
    let zip= element(By.css('.zip'));
    let image= element(By.css('.image'));
    let state = element(By.css('.state'));
    numBedrooms.sendKeys("1");
    numBathrooms.sendKeys(1);
    street.sendKeys("Oakmont Way");
    streetNumber.sendKeys(2085);
    zip.sendKeys("97401");
    state.sendKeys("OR");
    image.sendKeys(null);
    component.submit();
    expect(component.threadItem.id).not.toBe(undefined);
  });

});
