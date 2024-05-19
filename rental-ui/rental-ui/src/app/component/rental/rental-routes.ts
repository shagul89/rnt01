import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductComponent } from './product/product.component';
import { OrderComponent } from './order/order.component';
import { ConfigurationComponent } from './configuration/configuration.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthGuard } from '../../auth-guard';
import { UserComponent } from './user/user.component';
import { AddEditUserModelComponent } from './user/add-edit-user-model/add-edit-user-model.component';
import { CustomerComponent } from './customer/customer.component';

const rentalRoutes: Routes = [
    { path: 'product', component: ProductComponent , canActivate: [AuthGuard] },
    { path: 'order', component: OrderComponent, canActivate: [AuthGuard] },
    { path: 'customer', component: CustomerComponent, canActivate: [AuthGuard] },
    { path: 'config', component: ConfigurationComponent, canActivate: [AuthGuard] },
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'addedituser', component: AddEditUserModelComponent, canActivate: [AuthGuard] }
]

@NgModule({
    imports: [RouterModule.forChild(rentalRoutes)],
    exports: [RouterModule]
})
export class RentalRoutingModule {

} 