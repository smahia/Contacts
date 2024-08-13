import {Component, OnInit} from '@angular/core';
import {NewContactRequest} from "../../request/NewContactRequest";
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ContactService} from "../../service/contact/contact.service";
import {ActivatedRoute} from "@angular/router";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import moment from "moment";
import Swal from "sweetalert2";
import {NewTelephoneRequest} from "../../request/NewTelephoneRequest";
import {NewAddressRequest} from "../../request/NewAddressRequest";
import {NewEmailRequest} from "../../request/NewEmailRequest";

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgClass,
    NgForOf
  ],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent implements OnInit {

  contact: NewContactRequest = new NewContactRequest();
  listId: number = 0;

  addContactForm = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      birthday: new FormControl(""),
      contactEmergency: new FormControl("false"),
      telephoneList: new FormArray([this.createTelephone()]),
      emailList: new FormArray([this.createEmail()]),
      addressList: new FormArray([this.createAddress()])
    }
  );

/**
  TELEPHONE
  */
  createTelephone(): FormGroup {
    return new FormGroup(
      {
        telephoneNumber: new FormControl(''),
        type: new FormControl('')
      }
    );
  }

  addTelephone() {
    // Get the FormArray telephoneList from the addContactForm
    const telephones = this.addContactForm.get('telephoneList') as FormArray;
    // When clicking on the button, add a new FormGroup from createTelephone(which returns a FormGroup) to the array
    telephones.push(this.createTelephone());
  }

  get telephones() {
    // Return the FormArray telephoneList from the addContactForm to use it in the template
    // In the HTML, iterate over the array of telephone forms
    return this.addContactForm.get('telephoneList') as FormArray;
  }

/**
  EMAIL
  */
  createEmail(): FormGroup {
    return new FormGroup(
      {
        email: new FormControl(''),
        type: new FormControl('')
      }
    );
  }

addEmail() {
    const email = this.addContactForm.get('emailList') as FormArray;
    email.push(this.createEmail());
  }

  get emails() {
    return this.addContactForm.get('emailList') as FormArray;
  }

  /**
  ADDRESS
  */
  createAddress(): FormGroup {
    return new FormGroup(
      {
        address: new FormControl(''),
        type: new FormControl('')
      }
    );
  }

addAddress() {
    const address = this.addContactForm.get('addressList') as FormArray;
    address.push(this.createAddress());
  }

  get address() {
    return this.addContactForm.get('addressList') as FormArray;
  }

  constructor(private contactService: ContactService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // Get the param of the url
    this.listId = this.route.snapshot.params['listId'];
  }

  handleSubmit() {

    if (this.addContactForm.valid) {

      this.contact.name = this.addContactForm.value.name!;
      this.contact.surname = this.addContactForm.value.surname!;

      // Check if a date has been selected
      if (this.addContactForm.value.birthday != "") {
        this.contact.birthday = moment.utc(this.addContactForm.value.birthday).format('DD/MM/yyyy');
      }

      this.addContactForm.value.contactEmergency == "true" ?
        this.contact.contactEmergency = true : this.contact.contactEmergency = false;

      // Check if there's a telephone because if not then does not iterate
      if (this.addContactForm.value.telephoneList) {
        for (const telephone of this.addContactForm.value.telephoneList) {
          if (telephone.telephoneNumber !== "" && telephone.type !== "") {
            let newTelephone = new NewTelephoneRequest();
            newTelephone.telephoneNumber = telephone.telephoneNumber;
            newTelephone.type = telephone.type;
            this.contact.telephoneList.push(newTelephone);
          }
        }
      }

    // Check if there's a email because if not then does not iterate
          if (this.addContactForm.value.emailList) {
            for (const email of this.addContactForm.value.emailList) {
              if (email.email !== "" && email.type !== "") {
                let newEmail = new NewEmailRequest();
                newEmail.email = email.email;
                newEmail.type = email.type;
                this.contact.emailList.push(newEmail);
              }
            }
          }

        // Check if there's a telephone because if not then does not iterate
              if (this.addContactForm.value.addressList) {
                for (const address of this.addContactForm.value.addressList) {
                  if (address.address !== "" && address.type !== "") {
                    let newAddress = new NewAddressRequest();
                    newAddress.address = address.address;
                    newAddress.type = address.type;
                    this.contact.addressesList.push(newAddress);
                  }
                }
              }

      this.contactService.addContact(this.listId, this.contact).subscribe(
        {
          next: value => {

            Swal.fire({
              title: "The contact has been successfully added to the list",
              icon: "success"
            }).then(
              () => {
                location.reload();
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
      )

    }

  }

}
