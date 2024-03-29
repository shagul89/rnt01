import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const adminModule = () => import('../admin/user-module').then((x) => x.UserModule);
const rentalModule = () => import('../rental/rental-module').then((x) => x.RentalModule);

const mainRoutes: Routes = [{
    path: 'admin',
    loadChildren: adminModule
  },
  {
    path: 'rental',
    loadChildren: rentalModule
  }]

@NgModule({
    imports: [RouterModule.forChild(mainRoutes)],
    exports: [RouterModule]
})
export class MainRoutingModule {

} 