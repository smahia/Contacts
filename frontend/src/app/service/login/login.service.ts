import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LogInRequest} from "../../request/logInRequest";
import {BehaviorSubject, Observable, tap} from "rxjs";
import {LogInResponse} from "../../response/logInResponse";
import moment from "moment";
import {Router} from "@angular/router";

const baseUrl = "http://localhost:8080/auth/login";

/**
 * In Angular, each time the page is reloaded all components are rebuilt. When a component calls a service,
 * (e.g. the navbar component), the service in the constructor checks if the token is valid,
 * sets the variable to true or false, and the component
 * in the OnInit,
 * that calls the service, subscribes to the isLoggedIn method to know if a user is authenticated or not.
 * This cycle is repeated each time the page is reloaded:
 * constructor of the service checks if the token is valid > the component
 * initiates the OnInit, subscribes to isLoggedIn and receives true or false.
 * As the token is in local storage, the observable returns true until it expires.
 */
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loggedInSubject = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {

    if (this.isTokenValid()) {
      // If the token is valid, set the value to true
      this.loggedInSubject.next(true);

    }

  }

  /**
   * We are receiving the result of the login call, containing the JWT and the expiresIn property,
   * and we are passing it directly to the setSession method
   */
  login(user: LogInRequest): Observable<LogInResponse> {
    return this.http.post<LogInResponse>(baseUrl, user)
      .pipe(
        tap(res => {
          this.setSession(res);
          // After the user has successfully log in, set the value to true
          this.loggedInSubject.next(true);
        })
      );
  }

  isTokenValid() {

    // Verify if there's a token in the local storage
    const token = localStorage.getItem('token');
    const expiresAt = localStorage.getItem('expires_at');

    if (token && expiresAt) {
      const expiresIn = moment(JSON.parse(expiresAt));
      // If the current time is less than the expiration time, the token is still valid
      return moment().isBefore(expiresIn);
    }

    return false;
  }

  /**
   * Inside setSession, we are storing the JWT directly in Local Storage in the token key entry
   * We are taking the current instant and the expiresInproperty, and using it to calculate the expiration timestamp
   * Then we are saving also the expiration timestamp as a numeric value in the expires_at Local Storage entry
   * @param authResult
   * @private
   */
  private setSession(authResult: LogInResponse) {
    // Add the expiration time of the token to the current time
    const expiresAt = moment().add(authResult.expiresIn, 'second');

    localStorage.setItem('token', authResult.token!);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
  }

  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("expires_at");

    this.loggedInSubject.next(false);

    this.router.navigateByUrl('/');
  }

  /**
   * Components must subscribe to this method if they want to know whether a user is authenticated or not
   */
  get isLoggedIn() {
    return this.loggedInSubject.asObservable();
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration!);
    return moment(expiresAt);
  }

  getToken() {
    return localStorage.getItem("token");
  }

}
