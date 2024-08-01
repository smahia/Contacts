import {Component, OnInit} from '@angular/core';
import {ListingService} from "../../service/listing/listing.service";
import {NgClass, NgFor, NgIf} from "@angular/common";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SearchListsPipe} from "../../pipe/search-lists.pipe";
import Swal from "sweetalert2";
import {NewListRequest} from "../../request/NewListRequest";
import {MyListsResponse} from "../../response/myListsResponse";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgFor,
    FormsModule,
    SearchListsPipe,
    NgClass,
    RouterLink
  ],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit {

  lists: any;
  searchListsFilter = '';
  isModalActive: boolean = false;
  isEditingModalActive: boolean = false;
  newList: NewListRequest = new NewListRequest();

  // This variable will hold the data to edit a list
  listToEdit: MyListsResponse = new MyListsResponse();

  newListForm = new FormGroup(
    {
      name: new FormControl('', Validators.required)
    }
  );

  editListForm = new FormGroup(
    {
      editedName: new FormControl('', Validators.required)
    }
  );

  constructor(private listingService: ListingService) {
  }

  ngOnInit(): void {

    this.listingService.showMyLists().subscribe(
      {
        next: value => {

          console.log(value);

          this.lists = value;

          //console.log(this.lists[0].name);

        },
        error: error => {

          console.log(error);

        }
      });
  }

  // For adding a new list modal
  toggleModal() {
    this.isModalActive = !this.isModalActive;
    // Reset the form so that the validation messages do not appear when the modal is closed
    this.newListForm.reset();
  }

  // For editing an existent list modal
  /**
   *
   * @param list The list that will be edited
   */
  openEditModal(list: MyListsResponse) {

    // When the edit modal is opened, the variable is filled with the list selected for editing.
    this.listToEdit = list;
    console.log(this.listToEdit);

    this.editListForm.controls['editedName'].setValue(this.listToEdit.name!);

    this.toggleEditModal();
  }

  toggleEditModal() {
    this.isEditingModalActive = !this.isEditingModalActive;
  }

  // For adding a new list
  handleSubmitForAdding() {

    if (this.newListForm.valid) {

      this.newList.name = this.newListForm.value.name!;

      this.listingService.addList(this.newList).subscribe({
          next: value => {

            Swal.fire({
              title: "Success",
              text: "The list has been saved",
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

  // For editing a list
  handleSubmitForEditing() {

    if (this.editListForm.valid) {

      // The list is already in the variable this.listToEdit, so here the name is updated with the input from the form
      this.listToEdit.name = this.editListForm.value.editedName!;

      this.listingService.editList(this.listToEdit, this.listToEdit.id!).subscribe(
        {
          next: value => {
            Swal.fire({
              title: "Success",
              text: "The list has been saved",
              icon: "success"
            }).then(() => {
              this.isEditingModalActive = false;
              window.location.reload();
            });

          },
          error: error => {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
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

  deleteList(listId: number, listName: String) {

    Swal.fire({
      title: "Are you sure you want to delete " + listName + "?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.listingService.deleteList(listId).subscribe({
          next: value => {
            Swal.fire({
              title: "Deleted!",
              text: "Your file has been deleted.",
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
            }).then(
              () => {
                this.isModalActive = false;
                window.location.reload();
              }
            )
          }
        });
      }
    });

  }

}
