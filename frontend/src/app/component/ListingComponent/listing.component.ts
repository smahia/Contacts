import {Component, OnInit} from '@angular/core';
import {ListingService} from "../../service/listing/listing.service";
import {AuthEmitter} from "../../emitter/authEmitter";

@Component({
  selector: 'app-listing',
  standalone: true,
  imports: [],
  templateUrl: './listing.component.html',
  styleUrl: './listing.component.scss'
})
export class ListingComponent implements OnInit {

  constructor(private listingService: ListingService) {
  }

  ngOnInit(): void {

    this.listingService.showMyLists().subscribe(
      {
        next: value => {
          console.log(value);
          AuthEmitter.authEmitter.emit(true);
        },
        error: error => {
          console.log(error);
          AuthEmitter.authEmitter.emit(false);
        }
      });
  }

}
