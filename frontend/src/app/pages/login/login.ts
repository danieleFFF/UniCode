import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Location } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector:'app-login',
  standalone:true,
  imports:[CommonModule, FormsModule, RouterLink],
  templateUrl:'./login.html',
  styleUrls:['./login.scss']
})
export class Login{
  email:string='';
  password:string='';
  showPassword:boolean=false;
  constructor(
    private router:Router,
    private authService:AuthService,
    private location:Location
  ){}
  onSubmit(){
    console.log('Tentativo di login con:',this.email,this.password);
    const credentials={
      email:this.email,
      password:this.password
    };
    this.authService.login(credentials).subscribe({
      next:()=>{
      console.log('Login Riuscito (dal componente)!');
      this.router.navigate(['/home']).then(()=>{});
    },
    error:(errore)=>{
      console.error('Login Fallito (dal componente):',errore);
      alert('Email o password errati. Riprova.');
    }
  });
  }
  goBack():void{
    this.location.back();
  }
  disableCopy(event:Event):void{
    event.preventDefault();
  }
  togglePassword():void{
    this.showPassword=!this.showPassword;
  }
}
