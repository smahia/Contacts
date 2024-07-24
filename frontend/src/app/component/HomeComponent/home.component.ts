import {Component, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {LoginService} from "../../service/login/login.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  userIsLoggedIn = false;

  constructor(private loginService: LoginService) {
  }

  ngOnInit(): void {

    this.loginService.isLoggedIn.subscribe(
      value => {

        this.userIsLoggedIn = value;
      }
    )
  }


}
