import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewTelephoneRequest} from "../../request/NewTelephoneRequest";
import {Observable} from "rxjs";
import {TelephoneResponse} from "../../response/TelephoneResponse";


const baseUrl = "http://localhost:8080/telephones/";

@Injectable({
  providedIn: 'root'
})
export class TelephoneService {

  constructor(private http: HttpClient) { }

  addTelephone(idContact: number, telephone: NewTelephoneRequest):Observable<TelephoneResponse> {
    return this.http.post(baseUrl + "add/" + idContact, telephone);
  }

  editTelephone(idTelephone: number, editedTelephone: NewTelephoneRequest):Observable<TelephoneResponse> {
    return this.http.put(baseUrl + "edit/" + idTelephone, editedTelephone);
  }

  deleteTelephone(idTelephone: number):Observable<any> {
    return this.http.delete(baseUrl + "delete/" + idTelephone);
  }

}
