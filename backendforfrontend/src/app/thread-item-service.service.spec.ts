import { TestBed } from '@angular/core/testing';

import { ThreadItemServiceService } from './thread-item-service.service';

describe('ThreadItemServiceService', () => {
  let service: ThreadItemServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThreadItemServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
