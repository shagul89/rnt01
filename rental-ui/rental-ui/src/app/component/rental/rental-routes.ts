import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductComponent } from './product/product.component';
import { OrderComponent } from './order/order.component';
import { ConfigurationComponent } from './configuration/configuration.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthGuard } from '../../auth-guard';

const adminRoutes: Routes = [
    { path: 'product', component: ProductComponent , canActivate: [AuthGuard] },
    { path: 'order', component: OrderComponent, canActivate: [AuthGuard] },
    { path: 'customer', component: OrderComponent, canActivate: [AuthGuard] },
    { path: 'config', component: ConfigurationComponent, canActivate: [AuthGuard] },
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }
]

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule]
})
export class RentalRoutingModule {

} 