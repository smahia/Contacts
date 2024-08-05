import {Component, Input} from '@angular/core';
import {NewTelephoneRequest} from "../../request/NewTelephoneRequest";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, NgIf} from "@angular/common";
import {TelephoneService} from "../../service/telephone/telephone.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-telephone, [add-telephone]',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './add-telephone.component.html',
  styleUrl: './add-telephone.component.scss'
})
export class AddTelephoneComponent {

  telephone: NewTelephoneRequest = new NewTelephoneRequest();
  @Input() idContact: number = 0;

  addTelephoneForm = new FormGroup(
    {
      telephoneNumber: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required)
    }
  );

  constructor(private telephoneService: TelephoneService) {
  }

  handleSubmit() {

    if (this.addTelephoneForm.valid) {

      this.telephone.telephoneNumber = this.addTelephoneForm.value.telephoneNumber!;
      this.telephone.type = this.addTelephoneForm.value.type!;


      this.telephoneService.addTelephone(this.idContact, this.telephone).subscribe(
        {
          next: value => {
            Swal.fire({
              title: "Phone has been successfully added",
              icon: "success"
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
