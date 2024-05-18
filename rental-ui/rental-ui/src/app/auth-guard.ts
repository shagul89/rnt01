import { inject } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
    CanActivateFn
} from '@angular/router';
import { map, take } from 'rxjs';
import { AuthService } from './component/rental/auth-service';

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authsService = inject(AuthService);
    const router = inject(Router);

    return authsService.isLoggedIn
        .pipe(
            take(1),
            map((isLoggedIn: boolean) => {
                if (!isLoggedIn) {
                    router.navigate(['/auth/login']);
                    return false;
                }
                return true;
            })
        )
}