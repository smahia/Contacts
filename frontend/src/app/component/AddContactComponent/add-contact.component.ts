import {Component, OnInit} from '@angular/core';
import {NewContactRequest} from "../../request/NewContactRequest";
import {FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ContactService} from "../../service/contact/contact.service";
import {ActivatedRoute} from "@angular/router";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import moment from "moment";
import Swal from "sweetalert2";
import {NewTelephoneRequest} from "../../request/NewTelephoneRequest";
import {NewAddressRequest} from "../../request/NewAddressRequest";
import {NewEmailRequest} from "../../request/NewEmailRequest";
import {ListingService} from "../../service/listing/listing.service";
import {MyListsResponse} from "../../response/myListsResponse";
import {GetContactResponse} from "../../response/GetContactResponse";

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgClass,
    NgForOf,
    FormsModule
  ],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent implements OnInit {

  contact: NewContactRequest = new NewContactRequest();
  listId: number = 0;
  lists: MyListsResponse[] | any;
  contacts: GetContactResponse[] | any;


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

  selectedContact: number = 0;


  onSelect(contact: number) {
    this.selectedContact = Number(contact); // Turns the value into a number
    console.log(this.selectedContact);

    this.contactService.getContact(this.selectedContact).subscribe(
      {
        next: value => {
          // reset the form from the previous selected contact
          this.addContactForm.reset();

          // TODO: fill in all inputs with the value
          this.addContactForm.controls['name'].setValue(value.name!);
          this.addContactForm.controls['surname'].setValue(value.surname!);
          // TODO: FIX BIRTHDAY
          //this.addContactForm.controls['birthday'].setValue(value.birthday!);

          value.contactEmergency == true ?
            this.addContactForm.controls['contactEmergency'].setValue("true") :
            this.addContactForm.controls['contactEmergency'].setValue("false");

          if (value.telephoneList && value.telephoneList.length > 0) {
            const telephones = this.addContactForm.get('telephoneList') as FormArray;

            // If an empty form exists...
            let index = 0;
            if(telephones.length == 1 && telephones.get('telephoneNumber') === null) {
              // Get the existent form
              const existentTelephoneForm = this.telephones.controls.at(index);
              // Add the telephone
              existentTelephoneForm!.patchValue({
                telephoneNumber: value.telephoneList[index].telephoneNumber,
                type: value.telephoneList[index].type
              })
              // Update the index, the next for will use this index to jump the first position if needed.
              index++;
            }

            // Add more telephones
            for (let i = index; i < value.telephoneList.length; i++) {
              // Create the form
              const telephoneGroup = this.createTelephone();
              // Add the telephone
              telephoneGroup.patchValue({
                telephoneNumber: value.telephoneList[i].telephoneNumber,
                type: value.telephoneList[i].type
              });
              telephones.push(telephoneGroup);
            }
          }


          // TODO: call the service to add the contact once all the inputs has been filled

        },
        error: err => {
          console.log(err);
        }
      }
    );

  }

  constructor(private contactService: ContactService,
              private route: ActivatedRoute,
              private listingService: ListingService) {}

  ngOnInit(): void {
    // Get the param of the url
    this.listId = this.route.snapshot.params['listId'];


    // Get all lists from the user
    this.listingService.showMyLists().subscribe(
      {
        next: value => {

          this.lists = value;

          // Get all contacts for each list
          for (let i = 0; i < this.lists.length; i++) {
            console.log(this.lists[i].id);

            this.contactService.getContactsByList(this.lists[i].id).subscribe(
              {
                next: value => {

                  this.contacts = value;

                  for (const contact of this.contacts) {
                    console.log(contact.name);
                  }

                },
                error: error => {

                  console.log(error);
                  Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Something went wrong trying to get all contacts by list!",
                  })

                }
              }
            );

          }

        },
        error: error => {

          console.log(error);
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Something went wrong trying to get all lists!",
          })

        }
      }
    );

  }

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
