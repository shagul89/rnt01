import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user/user.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from '../../auth-guard';
import { AddEditUserModelComponent } from './user/add-edit-user-model/add-edit-user-model.component';

const adminRoutes: Routes = [
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'addedituser', component: AddEditUserModelComponent, canActivate: [AuthGuard] }
]

@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule]
})
export class UserRoutingModule {

} 