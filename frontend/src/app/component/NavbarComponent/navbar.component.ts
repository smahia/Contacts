import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthEmitter} from "../../emitter/authEmitter";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {

  userIsLoggedIn = false;

  ngOnInit(): void {

    AuthEmitter.authEmitter.subscribe(
      value => {
        console.log("navbar: " + this.userIsLoggedIn);
        this.userIsLoggedIn = value;
      }
    );

  }

  logout() {

    localStorage.removeItem('token');
    AuthEmitter.authEmitter.emit(false);

  }

}
