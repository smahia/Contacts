import {Component, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthEmitter} from "../../emitter/authEmitter";

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

  username = null;
  userIsLoggedIn = false;

  ngOnInit(): void {
    AuthEmitter.authEmitter.subscribe(
      value => {
        this.userIsLoggedIn = value;
        console.log("home: " + this.userIsLoggedIn);
      }
    );
  }


}
