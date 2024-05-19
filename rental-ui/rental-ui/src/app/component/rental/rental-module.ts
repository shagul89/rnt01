import { NO_ERRORS_SCHEMA, NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MatDialogModule} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { ProductComponent } from './product/product.component';
import { OrderComponent } from './order/order.component';
import { OrderDetailsComponent } from './order/order-details/order-details.component';
import { AddEditProductModelComponent } from './product/add-edit-product-model/add-edit-product-model.component';
import { CustomerComponent } from './customer/customer.component';
import { AddEditCustomerModelComponent } from './customer/add-edit-customer-model/add-edit-customer-model.component';
import { ConfigurationComponent } from './configuration/configuration.component';
import { AddEditConfigModelComponent } from './configuration/add-edit-config-model/add-edit-config-model.component';
import { AddEditAddressComponent } from '../common/add-edit-address/add-edit-address.component';
import { NumberOnlyDirective } from '../common/directive/number-only';
import { RemoveSpecialCharPipe } from '../common/directive/remove-special-char';
import { UserComponent } from './user/user.component';
import { AddEditUserModelComponent } from './user/add-edit-user-model/add-edit-user-model.component';
import { RentalRoutingModule } from './rental-routes';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
  declarations: [
    ProductComponent,
    OrderComponent,
    OrderDetailsComponent,
    AddEditProductModelComponent,
    CustomerComponent,
    AddEditCustomerModelComponent,
    ConfigurationComponent,
    UserComponent,
    AddEditUserModelComponent,
    AddEditAddressComponent,
    RemoveSpecialCharPipe,
    DashboardComponent,
    NumberOnlyDirective,
    AddEditConfigModelComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatIconModule,
    RentalRoutingModule
  ],
  exports:[],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [
  ]
})

export class RentalModule {}
