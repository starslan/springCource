import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {TokenStorageService} from "../../service/token-storage.service";
import {UserService} from "../../service/user.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  isLoggedIn = false;
  isLoadData = false;
  user: User;

  constructor(private tokenService:TokenStorageService,
              private userService: UserService,
              private route: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();

    if (this.isLoggedIn){
      this.userService.getCurrentUser(null)
        .subscribe( data => {
          this.user = data;
          this.isLoadData = true;
        })
    }
  }

  logout(): void{
    this.tokenService.logOut();
    this.route.navigate(['/login'])
  }

}