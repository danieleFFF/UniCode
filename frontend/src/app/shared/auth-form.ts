import { Location } from '@angular/common';
import {Directive} from '@angular/core';

@Directive()
export abstract class AuthForm {

  email: string = '';
  password: string = '';
  showPassword: boolean = false;

  protected  constructor(protected location: Location) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  goBack(): void {
    this.location.back();
  }

  disableCopy(event: ClipboardEvent) {
    event.preventDefault();
  }
}
