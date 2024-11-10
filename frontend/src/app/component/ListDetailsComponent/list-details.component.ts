import {Component, OnInit} from '@angular/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MyListsResponse} from "../../response/myListsResponse";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ListingService} from "../../service/listing/listing.service";
import {NgForOf, NgIf, SlicePipe} from "@angular/common";
import {ContactDetailsComponent} from "../ContactDetailsComponent/contact-details.component";
import {ContactService} from "../../service/contact/contact.service";
import {GetContactResponse} from "../../response/GetContactResponse";
import {FormsModule} from "@angular/forms";
import {SearchContactsPipe} from "../../pipe/searchContacts/search-contacts.pipe";

@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [
    NgForOf,
    ContactDetailsComponent,
    RouterLink,
    NgIf,
    FormsModule,
    SearchContactsPipe,
    MatPaginatorModule,
    SlicePipe
  ],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.scss'
})
export class ListDetailsComponent implements OnInit {

  listId: number = 0;
  list: MyListsResponse = new MyListsResponse();
  contacts: GetContactResponse[] | any;
  searchContactsFilter = '';

  // Paginator settings
  pageSize = 5;
  pageIndex = 0;
  pageSizeOptions: number[] = [5, 10, 25, 100];

  constructor(private route: ActivatedRoute, private titleService: Title,
              private listingService: ListingService, private contactService: ContactService) {}

  ngOnInit(): void {

    // Get the param of the url
    this.listId = this.route.snapshot.params['id'];

    // Get the list
    this.listingService.getListById(this.listId).subscribe(
      {
        next: value => {
          this.list = value;

          // Modify component title
          this.titleService.setTitle("List " + this.list.name);

          console.log(this.list);
        },
        error: err => {
          console.log(err);
        }
      }
    );

    // Get the contacts of the list
    this.contactService.getContactsByList(this.listId).subscribe(
      {
        next: value => {
          this.contacts = value;
        },
        error: err => {
          console.log(err);
        }
      }
    );

  }

  // Paginator
  onPageChange(event: any) {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
  }

  // Sort buttons
  sortUp() {
    this.contacts.sort((a: GetContactResponse, b: GetContactResponse) => a.name!.localeCompare(b.name!));
  }
  sortDown() {
    this.contacts.sort((a: GetContactResponse, b: GetContactResponse) => b.name!.localeCompare(a.name!));
  }

}
