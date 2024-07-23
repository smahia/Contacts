import {Component} from '@angular/core';
import {NgClass, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LogInRequest} from "../../request/logInRequest";
import {LoginService} from "../../service/login/login.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {AuthEmitter} from "../../emitter/authEmitter";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  user: LogInRequest = new LogInRequest();

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  constructor(private loginService: LoginService, private router: Router) {
  }

  handleSubmit() {

    // Clear the error notification with the messages from the back
    document.getElementById("errors")!.classList.add("is-hidden");
    document.getElementById("errors")!.innerHTML = "";

    if (this.loginForm.valid) {

      this.user.username = this.loginForm.value.username!;
      this.user.password = this.loginForm.value.password!;

      this.loginService.login(this.user).subscribe(
        {
          next: value => {

            // We are storing this JWT token in local storage so we can retrieve it from there whenever required
            localStorage.setItem('token', value.token!);

            console.log(value);
            console.log("User is logged in");


            const Toast = Swal.mixin({
              toast: true,
              position: "top-end",
              showConfirmButton: false,
              timer: 3000,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
              }
            });
            Toast.fire({
              icon: "success",
              title: "Signed in successfully"
            });

            this.router.navigateByUrl('/');

            AuthEmitter.authEmitter.emit(true);


          },
          error: error => {
            console.log(error.error.description);

            document.getElementById("errors")!.classList.remove("is-hidden");
            document.getElementById("errors")!.innerHTML = error.error.description;

            AuthEmitter.authEmitter.emit(false);

          }
        });

    }

  }

}
