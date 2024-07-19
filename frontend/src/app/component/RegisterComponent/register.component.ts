import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {RegistrationService} from "../../service/registration.service";
import {User} from "../../model/user";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  user: User = new User();

  registrationForm = new FormGroup(
    {
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordConfirmation: new FormControl("", Validators.required)
    }
  );

  constructor(private registrationService: RegistrationService) {
  }

  handleSubmit() {

    if (this.registrationForm.valid) {

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
              icon: "success"
            }).then(
              () => {
                this.registrationForm.reset();
              }
            );
          },
          error: error => {
            console.log(error.error.message)
          }
        });

    }

  }

}
