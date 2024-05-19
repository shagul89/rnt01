import { Routes } from '@angular/router';

const authModule = () => import('./component/auth/auth-module').then((x) => x.AuthModule);
const rentalModule = () => import('./component/rental/rental-module').then((x) => x.RentalModule);

export const routes: Routes = [
  {
    path: 'rental',
    loadChildren: rentalModule,
  }, {
    path: 'auth',
    loadChildren: authModule
  }];
