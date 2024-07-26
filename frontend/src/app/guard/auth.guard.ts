import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable, tap} from 'rxjs';
import {LoginService} from "../service/login/login.service";

/**
 * This guard redirects to login if an anonymous user tries to access a page reserved for logged in users
 * https://stackoverflow.com/questions/70937412/redirect-user-to-home-page-when-user-does-not-have-any-permission-in-angular13
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    return this.loginService.isLoggedIn.pipe(
      tap(isLoggedIn => {
        if (!isLoggedIn) {
          this.router.navigateByUrl('/login'); // go to login if not authenticated
        }
      })
    );
  }
}
