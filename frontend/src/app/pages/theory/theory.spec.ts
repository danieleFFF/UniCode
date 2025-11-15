import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Theory } from './theory';

describe('Theory', () => {
  let component: Theory;
  let fixture: ComponentFixture<Theory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Theory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Theory);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
