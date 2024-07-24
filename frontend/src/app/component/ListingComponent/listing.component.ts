import {Component, OnInit} from '@angular/core';
import {ListingService} from "../../service/listing/listing.service";
import {NgFor, NgIf} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgFor
  ],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit {

  // TODO: implement search bar?
  lists: any;

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
