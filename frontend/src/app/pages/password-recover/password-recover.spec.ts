import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRecover } from './password-recover';

describe('PasswordRecover', () => {
  let component: PasswordRecover;
  let fixture: ComponentFixture<PasswordRecover>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasswordRecover]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasswordRecover);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
