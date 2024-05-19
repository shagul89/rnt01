import { Component,  ViewChild, ViewEncapsulation  } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AddEditAddressComponent } from '../../../common/add-edit-address/add-edit-address.component';
import { User } from '../../../model/user';
import { Router  } from '@angular/router';
import { RentalService } from '../../rental-service';


@Component({
  selector: 'app-add-edit-user-model',
  templateUrl: './add-edit-user-model.component.html',
  styleUrl: './add-edit-user-model.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AddEditUserModelComponent {

  @ViewChild(AddEditAddressComponent) addressComponenet!: AddEditAddressComponent;

  isEdit = false;
  form: any;
  user: User = new User;
  userTypes = ['MANAGER', 'EMPLOYEE', 'CASHIER', 'ADMIN', 'SUPER_ADMIN'];
  status = ['ACTIVE', 'DEACTIVE'];
  gender = ['MALE', 'FEMALE'];

  constructor( private rentalService: RentalService, private router: Router,
     private toastr: ToastrService, private formBuilder: FormBuilder) {
    /*if (data && data.user) {
      this.user = data.user;
      this.isEdit = true;
    }*/
  }

  ngOnInit() {
    let inputData = {};
    if(inputData && Object.keys(inputData).length !== 0){
    this.user= Object.assign(this.user, inputData);
    this.isEdit = true;
    }

    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      status: ['', Validators.required],
      userType: ['', Validators.required],
      gender: ['', Validators.required],
      contactnumber: ['', Validators.required]
    });
    if (this.isEdit) {
      this.form.controls.firstName.patchValue(this.user.firstName);
      this.form.controls.lastName.patchValue(this.user.lastName);
      this.form.controls.email.patchValue(this.user.email);
      this.form.controls.status.patchValue(this.user.status);
      this.form.controls.userType.patchValue(this.user.userType);
      this.form.controls.gender.patchValue(this.user.gender);
      this.form.controls.contactnumber.patchValue(this.user.contactnumber);
    }
  }

  ngAfterViewInit() {
    if (this.isEdit) {
      setTimeout(() => {
        //this.addressComponenet.initAddress(this.user.userAddress);
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
    this.user.gender = this.form.get('gender').value;
    this.user.contactnumber = this.form.get('contactnumber').value;
    //this.user.userAddress = this.addressComponenet.addressList;
  }

  submit() {
    if (this.form.invalid) {
      return;
    }
    /*if (this.addressComponenet.isError) {
      return;
    }*/
    this.convertFormToUser();
    if(this.isEdit){
      this.update();
    } else {
      this.save();
    }
  }

  save() {
    this.rentalService.saveUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data created successfully");
        this.user= {} as User;
        //this.addressComponenet.addressList =[];
        //this.dialog.close();
      },
      error: (error) => {
        this.toastr.error("User data failed to create");
      }
    });
  }

  update() {
    this.rentalService.updateUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data updated successfully");
        //this.dialog.close();
      },
      error: (error) => {
        this.toastr.success("User data failed to update");
      }
    });
  }

}
