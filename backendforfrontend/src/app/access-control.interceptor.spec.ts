import { TestBed } from '@angular/core/testing';

import { AccessControlInterceptor } from './access-control.interceptor';

describe('AccessControlInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      AccessControlInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: AccessControlInterceptor = TestBed.inject(AccessControlInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
