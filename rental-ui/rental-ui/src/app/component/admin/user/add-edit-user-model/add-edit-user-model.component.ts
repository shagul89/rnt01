import { Component, Inject, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../user-service';
import { AddEditAddressComponent } from '../../../common/add-edit-address/add-edit-address.component';
import { Address } from '../../../model/address';
import { MatTableDataSource } from '@angular/material/table';
import { User } from '../../../model/user';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-edit-user-model',
  templateUrl: './add-edit-user-model.component.html',
  styleUrl: './add-edit-user-model.component.css'
})
export class AddEditUserModelComponent {

  @ViewChild(AddEditAddressComponent) addressComponenet!: AddEditAddressComponent;

  isEdit = false;
  form: any;
  user: User = new User;
  userTypes = ['MANAGER', 'EMPLOYEE', 'CASHIER', 'ADMIN', 'SUPER_ADMIN'];
  status = ['ACTIVE', 'DEACTIVE'];

  constructor(public dialog: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) data: any, private userService: UserService, private router: Router,
     private toastr: ToastrService, private formBuilder: FormBuilder) {
    if (data && data.user) {
      this.user = data.user;
      this.isEdit = true;
    }
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      status: ['', Validators.required],
      userType: ['', Validators.required]
    });
    if (this.isEdit) {
      this.form.controls.firstName.patchValue(this.user.firstName);
      this.form.controls.lastName.patchValue(this.user.lastName);
      this.form.controls.email.patchValue(this.user.email);
      this.form.controls.status.patchValue(this.user.status);
      this.form.controls.userType.patchValue(this.user.userType);
    }
  }

  ngAfterViewInit() {
    if (this.isEdit) {
      setTimeout(() => {
        this.addressComponenet.initAddress(this.user.userAddress);
      }, 0);
    }
  }

  get f() {
    return this.form.formcontrols;
  }

  convertFormToUser() {
    this.user.lastName = this.form.get('lastName').value;
    this.user.firstName = this.form.get('firstName').value;
    this.user.email = this.form.get('email').value;
    this.user.userType = this.form.get('userType').value;
    this.user.status = this.form.get('status').value;
    this.user.userAddress = this.addressComponenet.addressList;
  }

  submit() {
    if (this.form.invalid) {
      return;
    }
    if (this.addressComponenet.isError) {
      return;
    }
    this.convertFormToUser();
    if(this.isEdit){
      this.update();
    } else {
      this.save();
    }
  }

  save() {
    this.userService.saveUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data created successfully");
        this.dialog.close();
      },
      error: (error) => {
        this.toastr.error("User data failed to create");
      }
    });
  }

  update() {
    this.userService.updateUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data updated successfully");
        this.dialog.close();
      },
      error: (error) => {
        this.toastr.success("User data failed to update");
      }
    });
  }

}
