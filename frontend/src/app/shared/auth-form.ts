import { Location } from '@angular/common';
import {Directive} from '@angular/core';

@Directive()
export abstract class AuthForm {

  email: string = '';
  password: string = '';
  showPassword: boolean = false;

  protected  constructor(private location: Location) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  disableCopy(event: ClipboardEvent) {
    event.preventDefault();
  }
}
