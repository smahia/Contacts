import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ChangePasswordRequest} from "../../request/ChangePasswordRequest";
import {Observable} from "rxjs";

const baseUrl = "http://localhost:8080/users/changePassword";
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  changePassword(changePassword: ChangePasswordRequest): Observable<any> {
    return this.http.put(baseUrl, changePassword);
  }
}
