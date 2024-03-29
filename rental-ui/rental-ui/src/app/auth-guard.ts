import { inject } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
    CanActivateFn
} from '@angular/router';
import { AuthService } from './component/auth/auth-service';

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authsService = inject(AuthService);
    const router = inject(Router);

    if(authsService.isLoggedIn()){
        return true;
    } else {
        router.navigate(['/auth/login']);
        return false;
    }
}