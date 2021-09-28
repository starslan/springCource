import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

const AUTH_API = environment.apiUrl + '/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public login(user: any):Observable<any>{
    console.log(user);
    return  this.http.post(AUTH_API + '/signin', {
      username: user.username,
      password: user.password
    })
  }

  public register(user: any): Observable<any>{
    return  this.http.post(AUTH_API + '/signup', {
      username: user.username,
      password: user.password,
      confirmPassword:  user.confirmPassword,
      email: user.email,
      firstname: user.firstname,
      lastname: user.lastname,

    })
  }


}
