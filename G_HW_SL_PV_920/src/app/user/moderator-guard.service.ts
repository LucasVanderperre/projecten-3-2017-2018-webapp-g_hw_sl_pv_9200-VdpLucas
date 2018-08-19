import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { CanActivate } from '@angular/router';


@Injectable()
export class ModeratorGuardService implements CanActivate{

  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.mod) {
      return true;
    }
    this.authService.redirectUrl = state.url;
    console.log(this.authService.mod)
    this.router.navigate(['/pagenotfound']);
    return false;
  }

}
