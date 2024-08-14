import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewEmailRequest} from "../../request/NewEmailRequest";
import {Observable} from "rxjs";
import {EmailResponse} from "../../response/EmailResponse";

const baseUrl = "http://localhost:8080/emails/";

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(private http: HttpClient) { }

  addEmail(idContact: number, email: NewEmailRequest): Observable<EmailResponse> {
    return this.http.post(baseUrl + "add/" + idContact, email);
  }

  editEmail(idEmail: number, editedEmail: NewEmailRequest): Observable<EmailResponse> {
    return this.http.put(baseUrl + "edit/" + idEmail, editedEmail);
  }

  deleteEmail(idEmail: number): Observable<any> {
    return this.http.delete(baseUrl + "delete/" + idEmail);
  }
}
