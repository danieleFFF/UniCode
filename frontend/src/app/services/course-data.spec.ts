import { TestBed } from '@angular/core/testing';

import { CourseData } from './course-data';

describe('CourseData', () => {
  let service: CourseData;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseData);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
