import {Component, OnInit} from '@angular/core';
import {GetContactResponse} from "../../response/GetContactResponse";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ContactService} from "../../service/contact/contact.service";
import moment from "moment";
import Swal from "sweetalert2";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AddTelephoneComponent} from "../AddTelephoneComponent/add-telephone.component";
import {AddEmailComponent} from "../AddEmailComponent/add-email.component";
import {AddAddressComponent} from "../AddAddressComponent/add-address.component";
import {TelephoneService} from "../../service/telephone/telephone.service";
import {EmailService} from "../../service/email/email.service";
import {AddressService} from "../../service/address/address.service";
import {TelephoneResponse} from "../../response/TelephoneResponse";
import {EmailResponse} from "../../response/EmailResponse";
import {AddressResponse} from "../../response/AddressResponse";
import {EditContactRequest} from "../../request/EditContactRequest";
import {ListingService} from "../../service/listing/listing.service";
import {MyListsResponse} from "../../response/myListsResponse";
import {NewContactRequest} from "../../request/NewContactRequest";

@Component({
  selector: 'app-contact-details, [contact-details]',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    NgClass,
    AddTelephoneComponent,
    AddEmailComponent,
    AddAddressComponent,
    RouterLink
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
  isModalActive: boolean = false;
  isCopyContactModalActive: boolean = false;
  // This variable contains the same data of the contact so it can be copied to another list
  copiedContact: NewContactRequest = new NewContactRequest();
  // This variable contains all the lists belonging to this user,
  // except the list in which the current contact is present
  lists: MyListsResponse[] = [];
  // This variable will hold the new information of the edited contact
  editedContact: EditContactRequest = new EditContactRequest();

  selectedList: number = 0;
  // This variable allows to hold a default value in the select list dropdown
  // Example: https://angular-ivy-dyexib.stackblitz.io
  selectedItem = null;

  editContactForm = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      birthday: new FormControl(''),
      contactEmergency: new FormControl("false")
    }
  );


  constructor(private route: ActivatedRoute, private titleService: Title,
              private contactService: ContactService, private router: Router,
              private telephoneService: TelephoneService, private emailService: EmailService,
              private addressService: AddressService, private listingService: ListingService) {
  }

  ngOnInit(): void {

    // Get the param of the url
    this.contactId = this.route.snapshot.params['id'];

    this.contactService.getContact(this.contactId).subscribe(
      {
        next: value => {
          this.contact = value;
          this.titleService.setTitle(this.contact.name + " " + this.contact.surname);
        },
        error: err => {
          console.log(err);
        }
      }
    );

    // 1. Bring all the lists that belong to the contact to show them in the modal for copying a contact
    this.listingService.showMyLists().subscribe(
      {
        next: value => {

          const lists = value as MyListsResponse[] // Dynamic Cast
          lists.forEach(list => {

            // 2. Do not show the list to which the contact belongs
            if (this.contact.listId !== list.id) {
              this.lists.push(list);
            }

          })

        },
        error: err => {
          console.log(err);
        }
      }
    );
  }


  /*
  #############################################################################
  EDIT A TELEPHONE
   */
  // Form for editing a telephone
  editingTelephoneForm = new FormGroup({
    telephoneNumber: new FormControl('', Validators.required),
    type: new FormControl('', Validators.required),
  });

  // To control which phone number is being edited
  editingTelephoneIndex = -1;

  // Editing an specific phone number
  startEditingTelephone(index: number) {
    this.editingTelephoneIndex = index;
    // This fills in the input from the form with the current data
    if (this.contact.telephoneList != null) {
      const telephone = this.contact.telephoneList[index];
      this.editingTelephoneForm.patchValue({
        telephoneNumber: telephone.telephoneNumber,
        type: telephone.type,
      });
    }
  }

  // To save changes in the database calling the service if everything is correct
  saveChangesTelephone() {
    if (this.editingTelephoneIndex !== -1) {
      if (this.contact.telephoneList != null) {

        if (this.editingTelephoneForm.valid) {

          const telephone = this.contact.telephoneList[this.editingTelephoneIndex];
          telephone.telephoneNumber = this.editingTelephoneForm.value.telephoneNumber!;
          telephone.type = this.editingTelephoneForm.value.type!;

          this.telephoneService.editTelephone(telephone.id!, telephone).subscribe(
            {
              next: value => {

                Swal.fire({
                  title: "Telephone has been successfully changed",
                  icon: "success"
                }).then(
                  () => {
                    this.editingTelephoneIndex = -1; // Reiniciar el índice de edición
                  }
                );

              },
              error: error => {
                console.log(error);
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
          )

        }
      }
    }
  }

  // To cancel editing a phone
  cancelEditingTelephone() {
    this.editingTelephoneIndex = -1;
  }

  /*
  #############################################################################
  EDIT AN EMAIL
   */
  // Form for editing an email
  editingEmailForm = new FormGroup({
    email: new FormControl('', Validators.required),
    type: new FormControl('', Validators.required),
  });

  // To control which email is being edited
  editingEmailIndex = -1;

  // Editing an specific email
  startEditingEmail(index: number) {
    this.editingEmailIndex = index;
    // This fills in the input from the form with the current data
    if (this.contact.emailList != null) {
      const email = this.contact.emailList[index];
      this.editingEmailForm.patchValue({
        email: email.email,
        type: email.type,
      });
    }
  }

  // To save changes in the database calling the service if everything is correct
  saveChangesEmail() {
    if (this.editingEmailIndex !== -1) {
      if (this.contact.emailList != null) {

        if (this.editingEmailForm.valid) {

          const email = this.contact.emailList[this.editingEmailIndex];
          email.email = this.editingEmailForm.value.email!;
          email.type = this.editingEmailForm.value.type!;

          this.emailService.editEmail(email.id!, email).subscribe(
            {
              next: value => {

                Swal.fire({
                  title: "Email has been successfully changed",
                  icon: "success",
                  confirmButtonColor: "#00D1B2",
                }).then(
                  () => {
                    this.editingEmailIndex = -1; // Reiniciar el índice de edición
                  }
                );

              },
              error: error => {
                console.log(error);
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
          )

        }
      }
    }
  }

  // To cancel editing an email
  cancelEditingEmail() {
    this.editingEmailIndex = -1;
  }

  /*
  #############################################################################
  EDIT AN ADDRESS
   */
  // Form for editing an address
  editingAddressForm = new FormGroup({
    address: new FormControl('', Validators.required),
    type: new FormControl('', Validators.required),
  });

  // To control which address is being edited
  editingAddressIndex = -1;

  // Editing an specific address
  startEditingAddress(index: number) {
    this.editingAddressIndex = index;
    // This fills in the input from the form with the current data
    if (this.contact.addressesList != null) {
      const address = this.contact.addressesList[index];
      this.editingAddressForm.patchValue({
        address: address.address,
        type: address.type,
      });
    }
  }

  // To save changes in the database calling the service if everything is correct
  saveChangesAddress() {
    if (this.editingAddressIndex !== -1) {
      if (this.contact.addressesList != null) {

        if (this.editingAddressForm.valid) {

          const address = this.contact.addressesList[this.editingAddressIndex];
          address.address = this.editingAddressForm.value.address!;
          address.type = this.editingAddressForm.value.type!;

          this.addressService.editAddress(address.id!, address).subscribe(
            {
              next: value => {

                Swal.fire({
                  title: "Address has been successfully changed",
                  icon: "success",
                  confirmButtonColor: "#00D1B2",
                }).then(
                  () => {
                    this.editingAddressIndex = -1; // Reiniciar el índice de edición
                  }
                );

              },
              error: error => {
                console.log(error);
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
          )

        }
      }
    }
  }

  // To cancel editing an email
  cancelEditingAddress() {
    this.editingAddressIndex = -1;
  }

  // For adding a edit contact modal
  toggleModal() {
    this.isModalActive = !this.isModalActive;

    this.editContactForm.controls['name'].setValue(this.contact.name!);
    this.editContactForm.controls['surname'].setValue(this.contact.surname!);

    if (this.contact.birthday != null) {
      // Convert birthday to ISO 8601 so it can be shown in the input from the modal
      const [day, month, year] = this.contact.birthday.split('/');
      const formattedDate = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
      this.editContactForm.controls['birthday'].setValue(formattedDate);
    }

    console.log(this.contact.birthday!);

    this.contact.contactEmergency == true ?
      this.editContactForm.controls['contactEmergency'].setValue("true")
      : this.editContactForm.controls['contactEmergency'].setValue("false")

  }

  closeModal() {
    this.isModalActive = !this.isModalActive;
    this.editContactForm.reset();
  }

  /**
   For editing contact personal details
   */
  handleSubmitEditingContact() {

    if (this.editContactForm.valid) {

      this.editedContact.name = this.editContactForm.value.name!;
      this.editedContact.surname = this.editContactForm.value.surname!;

      // Check if a date has been selected
      if (this.editContactForm.value.birthday != "") {
        this.editedContact.birthday = moment.utc(this.editContactForm.value.birthday).format('DD/MM/yyyy');
      }

      this.editContactForm.value.contactEmergency == "true" ?
        this.editedContact.contactEmergency = true : this.editedContact.contactEmergency = false;


      this.contactService.editContact(this.contactId, this.editedContact).subscribe({
          next: value => {

            Swal.fire({
              title: "Success",
              text: "The contact has been saved",
              confirmButtonColor: "#00D1B2",
              icon: "success"
            }).then(() => {
              this.isModalActive = false;
              window.location.reload();
            });

          },
          error: error => {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
              confirmButtonColor: "#00D1B2",
            }).then(
              () => {
                this.isModalActive = false;
                window.location.reload();
              }
            )
          }
        }
      );

    }

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
      confirmButtonColor: "#00D1B2",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.contactService.deleteContact(this.contactId, this.contact.listId!).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "The contact has been deleted.",
              confirmButtonColor: "#00D1B2",
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
              confirmButtonColor: "#00D1B2",
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

  deletePhone(telephone: TelephoneResponse) {

    let phoneId = telephone.id;

    Swal.fire({
      title: "Are you sure you want to delete the phone " + telephone.telephoneNumber + "?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#00D1B2",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.telephoneService.deleteTelephone(phoneId!).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "The phone has been deleted.",
              confirmButtonColor: "#00D1B2",
              icon: "success"
            }).then(
              () => {
                window.location.reload();
              }
            )
          },
          error: error => {
            console.log(error);
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
        });
      }
    });

  }

  deleteEmail(email: EmailResponse) {

    let emailId = email.id;

    Swal.fire({
      title: "Are you sure you want to delete the email " + email.email + "?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#00D1B2",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.emailService.deleteEmail(emailId!).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "The email has been deleted.",
              confirmButtonColor: "#00D1B2",
              icon: "success"
            }).then(
              () => {
                window.location.reload();
              }
            )
          },
          error: error => {
            console.log(error);
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
        });
      }
    });

  }

  deleteAddress(address: AddressResponse) {

    let addressId = address.id;

    Swal.fire({
      title: "Are you sure you want to delete the address " + address.address + "?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#00D1B2",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.addressService.deleteAddress(addressId!).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "The address has been deleted.",
              confirmButtonColor: "#00D1B2",
              icon: "success"
            }).then(
              () => {
                window.location.reload();
              }
            )
          },
          error: error => {
            console.log(error);
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
        });
      }
    });

  }

  // For adding a copy contact modal
  toggleCopyContactModal() {
    this.isCopyContactModalActive = !this.isCopyContactModalActive;
  }

  onSelectedListChanged(selectedList: number) {
    this.selectedList = Number(selectedList); // Turns the value into a number
    console.log('Selected list:', this.selectedList);

    // 3.  When the user selects a list, add the contact to that list
    this.copiedContact.name = this.contact.name;
    this.copiedContact.surname = this.contact.surname;
    this.copiedContact.birthday = this.contact.birthday;
    this.copiedContact.contactEmergency = this.contact.contactEmergency!;
    this.copiedContact.telephoneList = this.contact.telephoneList!;
    this.copiedContact.emailList = this.contact.emailList!;
    this.copiedContact.addressesList = this.contact.addressesList!;

    let selectedListName: string = "";

    // Iterate over the lists to show the name
    for (let i = 0; i < this.lists.length; i++) {
      if (this.lists[i].id == this.selectedList) {
        selectedListName = this.lists[i].name!;
      }
    }

    Swal.fire({
      title: "Are you sure you want to copy this contact to " + selectedListName + "?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#00D1B2",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, copy it!"
    }).then((result) => {
      if (result.isConfirmed) {

        this.contactService.addContact(this.selectedList, this.copiedContact).subscribe(
          {
            next: value => {
              Swal.fire({
                icon: "success",
                title: "The contact was successfully copied",
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
                text: "Something went wrong! The contact could not be copied",
              });
            }
          }
        );

      }
    });

  }

  closeCopyContactModal() {
    this.isCopyContactModalActive = !this.isCopyContactModalActive;
  }

}
