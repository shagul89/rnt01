import { Routes } from '@angular/router';

const authModule = () => import('./component/auth/auth-module').then((x) => x.AuthModule);
const mainModule = () => import('./component/menu/menu-module').then((x) => x.MenuModule);

export const routes: Routes = [
  {
    path: 'rental',
    loadChildren: mainModule,
  }, {
    path: 'auth',
    loadChildren: authModule
  }];
