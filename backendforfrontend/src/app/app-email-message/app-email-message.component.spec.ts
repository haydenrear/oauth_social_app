import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppEmailMessageComponent } from './app-email-message.component';

describe('AppEmailMessageComponent', () => {
  let component: AppEmailMessageComponent;
  let fixture: ComponentFixture<AppEmailMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppEmailMessageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppEmailMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
