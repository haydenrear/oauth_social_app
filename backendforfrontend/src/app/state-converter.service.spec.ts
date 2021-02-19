import { TestBed } from '@angular/core/testing';

import { StateConverterService } from './state-converter.service';

describe('StateConverterService', () => {
  let service: StateConverterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StateConverterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
