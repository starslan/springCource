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
      username: ['user123', Validators.compose([Validators.required])],
      email: ['email@email.ru', Validators.compose([Validators.email])],
      password: ['123', Validators.compose([Validators.required])],
      confirmPassword: ['123', Validators.compose([Validators.required])],
      firstname: ['Ruslan', Validators.compose([Validators.required])],
      lastname: ['Starostenko', Validators.compose([Validators.required])],
    })
  }


  submit(): void{
    this.authService.register({
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
      confirmPassword: this.registerForm.value.confirmPassword,
      email: this.registerForm.value.email,
      firstname: this.registerForm.value.firstname,
      lastname: this.registerForm.value.lastname,
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
