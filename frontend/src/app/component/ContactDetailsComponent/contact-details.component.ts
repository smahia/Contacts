import {Component, OnInit} from '@angular/core';
import {GetContactResponse} from "../../response/GetContactResponse";
import {ActivatedRoute, Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ContactService} from "../../service/contact/contact.service";
import Swal from "sweetalert2";
import {NgForOf, NgIf} from "@angular/common";
import {AddTelephoneComponent} from "../AddTelephoneComponent/add-telephone.component";
import {AddEmailComponent} from "../AddEmailComponent/add-email.component";
import {AddAddressComponent} from "../AddAddressComponent/add-address.component";

@Component({
  selector: 'app-contact-details, [contact-details]',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    AddTelephoneComponent,
    AddEmailComponent,
    AddAddressComponent
  ],
  templateUrl: './contact-details.component.html',
  styleUrl: './contact-details.component.scss'
})
export class ContactDetailsComponent implements OnInit {

  contactId: number = 0;
  contact: GetContactResponse = new GetContactResponse();
  displayTelephoneForm = false;
  displayEmailForm = false;
  displayAddressForm = false;


  constructor(private route: ActivatedRoute, private titleService: Title,
              private contactService: ContactService, private router: Router) {
  }

  ngOnInit(): void {

    // Get the param of the url
    this.contactId = this.route.snapshot.params['id'];

    this.contactService.getContact(this.contactId).subscribe(
      {
        next: value => {
          this.contact = value;
          this.titleService.setTitle(this.contact.name + " " +  this.contact.surname);
          console.log(this.contact);
        },
        error: err => {
          console.log(err);
        }
      }
    );

  }

  showTelephoneForm() {
    this.displayTelephoneForm = !this.displayTelephoneForm;
  }

  showAddressForm() {
    this.displayAddressForm = !this.displayAddressForm;
  }

  showEmailForm() {
    this.displayEmailForm = !this.displayEmailForm;
  }

  deleteContact(listId: number, contactId: number) {

    Swal.fire({
      title: "Are you sure you want to delete the contact " + this.contact.name + " " + this.contact.surname + "?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.contactService.deleteContact(this.contactId, this.contact.listId!).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "The contact has been deleted.",
              icon: "success"
            }).then(
              () => {
                this.router.navigateByUrl('/list/' + listId);
              }
            )
          },
          error: error => {
            console.log(error);
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
        });
      }
    });

  }

}
