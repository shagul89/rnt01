import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user/user.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from '../../auth-guard';

const adminRoutes: Routes = [
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] }
]

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule]
})
export class UserRoutingModule {

} 