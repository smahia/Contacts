import {Component, OnInit} from '@angular/core';
import {ListingService} from "../../service/listing/listing.service";
import {NgFor, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SearchListsPipe} from "../../pipe/search-lists.pipe";

@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgFor,
    FormsModule,
    SearchListsPipe
  ],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit {
  
  lists: any;
  searchListsFilter = '';

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

}
