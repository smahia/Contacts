import {Component, Input} from '@angular/core';
import {NewEmailRequest} from "../../request/NewEmailRequest";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {EmailService} from "../../service/email/email.service";
import Swal from "sweetalert2";
import {NgClass, NgIf} from "@angular/common";

@Component({
  selector: 'app-add-email',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './add-email.component.html',
  styleUrl: './add-email.component.scss'
})
export class AddEmailComponent {

  email: NewEmailRequest = new NewEmailRequest();
  @Input() idContact: number = 0;

  addEmailForm = new FormGroup(
    {
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      type: new FormControl('', Validators.required)
    }
  );

  constructor(private emailService: EmailService) {
  }

  handleSubmit() {

    if (this.addEmailForm.valid) {

      this.email.email = this.addEmailForm.value.email!;
      this.email.type = this.addEmailForm.value.type!;


      this.emailService.addEmail(this.idContact, this.email).subscribe(
        {
          next: value => {
            Swal.fire({
              title: "Email has been successfully added",
              icon: "success",
              confirmButtonColor: "#00D1B2",
            }).then(
              () => {
                window.location.reload();
              }
            );
          },
          error: err => {
            console.log(err);
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
              confirmButtonColor: "#00D1B2",
            }).then(
              () => {
                window.location.reload();
              }
            )
          }
        }
      );

    }

  }

}
