import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginService} from "../service/login/login.service";

/**
 * Injectable class which will intercept all the http requests and append the authorization header with our JWT token
 * We first start by retrieving the JWT string from Local Storage directly
 * then we are going to check if the JWT is present
 * if the JWT is not present, then the request goes through to the server unmodified
 * if the JWT is present, then we will clone the HTTP headers, and add an extra Authorization header,
 * which will contain the JWT
 * And with this in place, the JWT that was initially created on the Authentication server,
 * is now being sent with each request to the Application server.
 * https://www.codewithazzan.com/registration-login-jwt-authentication-in-angular/#user-login-and-storing-jwt
 * https://blog.angular-university.io/angular-jwt-authentication/
 * It is necessary to declare it in providers in the app.config.ts, as well as in the provideHttpClient, so all
 * HTTP requests use the interceptor
 */

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.loginService.getToken();

    if (token) {
      const cloned = request.clone({
        headers: request.headers.set("Authorization",
          "Bearer " + token)
      });


      return next.handle(cloned);

    } else {

      return next.handle(request);
    }
  }

}
