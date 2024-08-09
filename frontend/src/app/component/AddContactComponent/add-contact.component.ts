import {Component, OnInit} from '@angular/core';
import {NewContactRequest} from "../../request/NewContactRequest";
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ContactService} from "../../service/contact/contact.service";
import {ActivatedRoute} from "@angular/router";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import moment from "moment";
import Swal from "sweetalert2";
import {NewTelephoneRequest} from "../../request/NewTelephoneRequest";

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
      telephoneList: new FormArray([this.createTelephone()])
    }
  );

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

  createEmail(): FormGroup {
    return new FormGroup(
      {
        email: new FormControl(''),
        type: new FormControl('')
      }
    );
  }

  createAddress(): FormGroup {
    return new FormGroup(
      {
        address: new FormControl(''),
        type: new FormControl('')
      }
    );
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
