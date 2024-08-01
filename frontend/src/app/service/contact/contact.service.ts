import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetContactResponse} from "../../response/GetContactResponse";

const baseUrl = "http://localhost:8080/contacts/";

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) { }

  getContactsByList(listId: number): Observable<GetContactResponse> {
    return this.http.get(baseUrl + "getContactsByList/" + listId);
  }

  getContact(contactId: number): Observable<GetContactResponse> {
    return this.http.get(baseUrl + contactId);
  }
}
