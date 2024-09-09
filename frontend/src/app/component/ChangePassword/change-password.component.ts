import { Component } from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import Swal from "sweetalert2";
import {NgClass, NgIf} from "@angular/common";
import {UserService} from "../../service/user/user.service";
import {ChangePasswordRequest} from "../../request/ChangePasswordRequest";

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.scss'
})
export class ChangePasswordComponent {

  changePasswordForm = new FormGroup({
    oldPassword: new FormControl('', Validators.required),
    newPassword: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required)
  });

  changePasswordRequest: ChangePasswordRequest = new ChangePasswordRequest();

  constructor(private userService: UserService) {
  }

  handleSubmit() {

    // Clear the error notification with the messages from the back
    document.getElementById("errors")!.classList.add("is-hidden");
    document.getElementById("errors")!.innerHTML = "";

    if (this.changePasswordForm.valid) {

      this.changePasswordRequest.oldPassword = this.changePasswordForm.value.oldPassword!;
      this.changePasswordRequest.newPassword = this.changePasswordForm.value.newPassword!;
      this.changePasswordRequest.confirmPassword = this.changePasswordForm.value.confirmPassword!;

      this.userService.changePassword(this.changePasswordRequest).subscribe(
        {
          next: value => {

            Swal.fire({
              title: "Your password has been successfully changed",
              icon: "success"
            }).then(
              () => {
                window.location.reload();
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
