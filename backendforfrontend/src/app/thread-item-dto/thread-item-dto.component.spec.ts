import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreadItemDTOComponent } from './thread-item-dto.component';

describe('ThreadItemDTOComponent', () => {
  let component: ThreadItemDTOComponent;
  let fixture: ComponentFixture<ThreadItemDTOComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThreadItemDTOComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreadItemDTOComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
