import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewAddressRequest} from "../../request/NewAddressRequest";
import {Observable} from "rxjs";
import {AddressResponse} from "../../response/AddressResponse";

const baseUrl = "http://localhost:8080/address/";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private http: HttpClient) { }

  addAddress(idContact: number, address: NewAddressRequest): Observable<AddressResponse> {
    return this.http.post(baseUrl + "add/" + idContact, address);
  }

  deleteAddress(idAddress: number) {
    return this.http.delete(baseUrl + "delete/" + idAddress);
  }
}
