import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LogInRequest} from "../../request/logInRequest";
import {Observable, tap} from "rxjs";
import {LogInResponse} from "../../response/logInResponse";

const baseUrl = "http://localhost:8080/auth/login";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  login(user: LogInRequest): Observable<LogInResponse> {
    return this.http.post(baseUrl, user);
  }

}
