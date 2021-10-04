import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModules} from "./material-modules";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders, AuthInterceptorService} from "./helper/auth-interceptor.service";

import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {errorInterceptorProvider} from "./helper/error-interceptor.service";
import { NavigationComponent } from './layout/navigation/navigation.component';
import { IndexComponent } from './layout/index/index.component';
import { ProfileComponent } from './user/profile/profile.component';
import { UserPostsComponent } from './user/user-posts/user-posts.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { AddPostsComponent } from './user/add-posts/add-posts.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    IndexComponent,
    ProfileComponent,
    UserPostsComponent,
    EditUserComponent,
    AddPostsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModules,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [authInterceptorProviders, errorInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
