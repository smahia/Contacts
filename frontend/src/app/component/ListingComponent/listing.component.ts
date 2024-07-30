import {Component, OnInit} from '@angular/core';
import {ListingService} from "../../service/listing/listing.service";
import {NgClass, NgFor, NgIf} from "@angular/common";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {SearchListsPipe} from "../../pipe/search-lists.pipe";
import Swal from "sweetalert2";
import {NewListRequest} from "../../request/NewListRequest";

@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgFor,
    FormsModule,
    SearchListsPipe,
    NgClass
  ],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit {

  lists: any;
  searchListsFilter = '';
  isModalActive: boolean = false;
  newList: NewListRequest = new NewListRequest();

  newListForm = new FormGroup(
    {
      name: new FormControl('', Validators.required)
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

  toggleModal() {
    this.isModalActive = !this.isModalActive;
  }

  handleSubmit() {

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
          }
        });
      }
    });

  }

}
