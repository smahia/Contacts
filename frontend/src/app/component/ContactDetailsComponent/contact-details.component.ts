import {Component, OnInit} from '@angular/core';
import {GetContactResponse} from "../../response/GetContactResponse";
import {ActivatedRoute} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {ContactService} from "../../service/contact/contact.service";

@Component({
  selector: 'app-contact-details, [contact-details]',
  standalone: true,
  imports: [],
  templateUrl: './contact-details.component.html',
  styleUrl: './contact-details.component.scss'
})
export class ContactDetailsComponent implements OnInit {

  contactId: number = 0;
  contact: GetContactResponse = new GetContactResponse();

  constructor(private route: ActivatedRoute, private titleService: Title, private contactService: ContactService) {
  }

  ngOnInit(): void {

    // Get the param of the url
    this.contactId = this.route.snapshot.params['id'];

    this.contactService.getContact(this.contactId).subscribe(
      {
        next: value => {
          this.contact = value;
          console.log(this.contact);
        },
        error: err => {
          console.log(err);
        }
      }
    );

  }

}
