import {Component, Input} from '@angular/core';
import {AddressService} from "../../service/address/address.service";
import {NewAddressRequest} from "../../request/NewAddressRequest";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import Swal from "sweetalert2";
import {NgClass, NgIf} from "@angular/common";

@Component({
  selector: 'app-add-address',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './add-address.component.html',
  styleUrl: './add-address.component.scss'
})
export class AddAddressComponent {

  address: NewAddressRequest = new NewAddressRequest();
  @Input() idContact: number = 0;

  addAddressForm = new FormGroup(
    {
      address: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required)
    }
  );

  constructor(private addressService: AddressService) {
  }

  handleSubmit() {

    if (this.addAddressForm.valid) {

      this.address.address = this.addAddressForm.value.address!;
      this.address.type = this.addAddressForm.value.type!;


      this.addressService.addAddress(this.idContact, this.address).subscribe(
        {
          next: value => {
            Swal.fire({
              title: "Address has been successfully added",
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
