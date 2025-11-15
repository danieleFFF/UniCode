import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesHome } from './courses-home';

describe('CoursesHome', () => {
  let component: CoursesHome;
  let fixture: ComponentFixture<CoursesHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoursesHome]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoursesHome);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
