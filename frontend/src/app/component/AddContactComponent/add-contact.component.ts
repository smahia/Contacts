import {Component, OnInit} from '@angular/core';
import {NewContactRequest} from "../../request/NewContactRequest";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ContactService} from "../../service/contact/contact.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NgClass, NgIf} from "@angular/common";
import moment from "moment";
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent implements OnInit{

  contact: NewContactRequest = new NewContactRequest();
  listId: number = 0;

  addContactForm = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      birthday: new FormControl(""),
      contactEmergency: new FormControl("false")
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

  constructor(private contactService: ContactService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // Get the param of the url
    this.listId = this.route.snapshot.params['listId'];
  }

  handleSubmit() {

    if (this.addContactForm.valid) {

      this.contact.name = this.addContactForm.value.name!;
      this.contact.surname = this.addContactForm.value.surname!;

      if (this.addContactForm.value.birthday != "") {
        this.contact.birthday = moment.utc(this.addContactForm.value.birthday).format('DD/MM/yyyy');
      }

      this.addContactForm.value.contactEmergency == "true" ?
        this.contact.contactEmergency = true : this.contact.contactEmergency = false;


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
