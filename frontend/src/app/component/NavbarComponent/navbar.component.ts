import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {LoginService} from "../../service/login/login.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgOptimizedImage
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {

  userIsLoggedIn = false;

  constructor(private loginService: LoginService) {
  }

  ngOnInit(): void {

    this.loginService.isLoggedIn.subscribe(
      value => {

        this.userIsLoggedIn = value;
      }
    );

  }

  logout() {

    this.loginService.logout();
  }

}
