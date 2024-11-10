import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

/**
 * This Guard checks if the param of the url is a number or not
 */
@Injectable({
  providedIn: 'root'
})
export class NanGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (isNaN(+route.params['id'])) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
