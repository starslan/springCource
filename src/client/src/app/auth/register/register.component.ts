import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {TokenStorageService} from "../../service/token-storage.service";
import {NotificationService} from "../../service/notification.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

 public registerForm: FormGroup;

  constructor(
      private authService: AuthService,
      private tokenStorage: TokenStorageService,
      private notificationService: NotificationService,
      private router:Router,
      private fb: FormBuilder
    ) {

    }

  ngOnInit(): void {
    this.registerForm = this.createRegisterForm();
  }

  createRegisterForm(): FormGroup{
    return this.fb.group({
      username: ['', Validators.compose([Validators.required])],
      email: ['', Validators.compose([Validators.email])],
      password: ['', Validators.compose([Validators.required])],
      confirmPassword: ['', Validators.compose([Validators.required])],
      firstname: ['', Validators.compose([Validators.required])],
      latsname: ['', Validators.compose([Validators.required])],
    })
  }


  submit(): void{
    this.authService.register({
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
      email: this.registerForm.value.email,
      firstname: this.registerForm.value.firstname,
      latsname: this.registerForm.value.latsname,
    }).subscribe(data=>{
      console.log(data);
      this.tokenStorage.saveToken(data.token);
      this.tokenStorage.saveUser(data);
      this.notificationService.showSnackBar('Successfully Registered!');
      this.router.navigate(['/']);
      window.location.reload();
    }, error => {
      console.log(error);
      this.notificationService.showSnackBar(error.message);
    })
  }

}
