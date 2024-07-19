import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user";

const baseUrl = "http://localhost:8080/auth/signup";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  registration(user: User): Observable<User> {
    return this.http.post(baseUrl, user);
  }

}
