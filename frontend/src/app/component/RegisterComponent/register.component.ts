import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, NgIf} from "@angular/common";
import {RegistrationService} from "../../service/registration/registration.service";
import {SignUpRequest} from "../../request/signUpRequest";
import Swal from 'sweetalert2';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgClass
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  user: SignUpRequest = new SignUpRequest();
  passwordsAreIdentical = false;
  messagePasswordAreNotIdentical = false;

  registrationForm = new FormGroup(
    {
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordConfirmation: new FormControl("", Validators.required)
    }
  );

  constructor(private registrationService: RegistrationService, private router: Router) {
  }

  handleSubmit() {

    // Clear the error notification with the messages from the back
    document.getElementById("errors")!.classList.add("is-hidden");
    document.getElementById("errors")!.innerHTML = "";

    if (this.registrationForm.value.password?.toUpperCase() ===
      this.registrationForm.value.passwordConfirmation?.toUpperCase()) {

      this.passwordsAreIdentical = true;

      this.messagePasswordAreNotIdentical = false;

    } else {

      this.messagePasswordAreNotIdentical = true;

      this.passwordsAreIdentical = false;

    }

    if (this.registrationForm.valid && this.passwordsAreIdentical) {

      // non-null assertion operator it tells TypeScript that even though
      // something looks like it could be null, it can trust you that it's not
      this.user.username = this.registrationForm.value.username!;
      this.user.password = this.registrationForm.value.password!;
      this.user.passwordConfirmation = this.registrationForm.value.passwordConfirmation!;

      this.registrationService.registration(this.user).subscribe(
        {
          next: value => {
            this.user = value
            console.log(value)

            Swal.fire({
              title: "You have been successfully registered",
              icon: "success",
              confirmButtonColor: "#00D1B2",
            }).then(
              () => {
                this.registrationForm.reset();
                this.router.navigateByUrl('/login');
              }
            );
          },
          error: error => {
            console.log(error.error.message);

            document.getElementById("errors")!.classList.remove("is-hidden");
            document.getElementById("errors")!.innerHTML = error.error.message;

          }
        });

    }

  }

}
