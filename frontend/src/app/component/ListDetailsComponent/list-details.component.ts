import {Component, OnInit} from '@angular/core';
import {MyListsResponse} from "../../response/myListsResponse";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ListingService} from "../../service/listing/listing.service";
import {NgForOf} from "@angular/common";
import {ContactDetailsComponent} from "../ContactDetailsComponent/contact-details.component";
import {ContactService} from "../../service/contact/contact.service";
import {GetContactResponse} from "../../response/GetContactResponse";

@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [
    NgForOf,
    ContactDetailsComponent,
    RouterLink
  ],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.scss'
})
export class ListDetailsComponent implements OnInit {

  listId: number = 0;
  list: MyListsResponse = new MyListsResponse();
  contacts: GetContactResponse[] | any;

  constructor(private route: ActivatedRoute, private titleService: Title,
              private listingService: ListingService, private contactService: ContactService) {
  }

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

          console.log(this.contacts);
        },
        error: err => {
          console.log(err);
        }
      }
    );

  }

}
