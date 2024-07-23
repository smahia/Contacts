import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';

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
 */

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    /*if (localStorage.getItem('token'))
      return next.handle(
        request.clone(
          {
            setHeaders:
              {
                authorization: `Bearer ${localStorage.getItem('token')}`
              }
          })
      );*/

    const token = localStorage.getItem("token");

    if (token) {
      const cloned = request.clone({
        headers: request.headers.set("Authorization",
          "Bearer " + token)
      });

      console.log("clonada: " + cloned);

      return next.handle(cloned);
    } else {
      console.log("no clonada: " + request);
      return next.handle(request);
    }
  }

}
