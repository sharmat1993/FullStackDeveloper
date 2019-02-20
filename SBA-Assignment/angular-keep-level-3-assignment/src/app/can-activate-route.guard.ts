import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './services/authentication.service';
import { RouterService } from './services/router.service';

@Injectable()
export class CanActivateRouteGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService, private routerService: RouterService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      return this.authenticationService.isUserAuthenticated(
                  this.authenticationService.getBearerToken())
                  .then((onfulfilled) => {
                        if (onfulfilled) {
                            return onfulfilled;
                          }else {
                            this.routerService.routeToLogin();
                          }
                        },
                        (onrejected) => {
                          this.routerService.routeToLogin();
                          return onrejected;
                        }
                  );
  }
}
