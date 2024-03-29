import { NO_ERRORS_SCHEMA, NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import { UserComponent } from './user/user.component';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MatDialogModule} from '@angular/material/dialog';
import { AddEditUserModelComponent } from './user/add-edit-user-model/add-edit-user-model.component';
import { MatIconModule } from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import { UserRoutingModule } from './user-routes';
import { AddEditAddressComponent } from '../common/add-edit-address/add-edit-address.component';
import { RemoveSpecialCharPipe } from '../common/directive/remove-special-char';
import { NumberOnlyDirective } from '../common/directive/number-only';

@NgModule({
  declarations: [
    UserComponent,
    AddEditUserModelComponent,
    AddEditAddressComponent,
    RemoveSpecialCharPipe,
    NumberOnlyDirective
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
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
    MatCardModule
  ],
  exports:[],
  providers: [
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
})

export class UserModule {}
