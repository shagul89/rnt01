import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AddEditAddressComponent } from '../add-edit-address/add-edit-address.component';
import { User } from '../../../model/user';
import { ActivatedRoute, Router } from '@angular/router';
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
  userId:any;
  user: User = new User;
  userTypes = ['MANAGER', 'EMPLOYEE', 'CASHIER', 'ADMIN', 'SUPER_ADMIN'];
  status = ['ACTIVE', 'DEACTIVE'];
  gender = ['MALE', 'FEMALE'];
  addressList = [];

  constructor(private rentalService: RentalService, private router: Router,
    private toastr: ToastrService, private formBuilder: FormBuilder, private route : ActivatedRoute) {
      this.userId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    if(this.userId){
      this.isEdit = true;
    }

    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      status: ['', Validators.required],
      userType: ['', Validators.required],
      gender: ['', Validators.required]
    });

    if (this.isEdit) {
      this.getUserById();
    }else{
      this.form.controls.status.patchValue("ACTIVE");
    }
  }

  get f() {
    return this.form.formcontrols;
  }

  convertUserToForm(){
    this.form.controls.firstName.patchValue(this.user.firstName);
    this.form.controls.lastName.patchValue(this.user.lastName);
    this.form.controls.email.patchValue(this.user.email);
    this.form.controls.status.patchValue(this.user.status);
    this.form.controls.userType.patchValue(this.user.userType);
    this.form.controls.gender.patchValue(this.user.gender);
  }

  convertFormToUser() {
    this.user.lastName = this.form.get('lastName').value;
    this.user.firstName = this.form.get('firstName').value;
    this.user.email = this.form.get('email').value;
    this.user.userType = this.form.get('userType').value;
    this.user.status = this.form.get('status').value;
    this.user.gender = this.form.get('gender').value;
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
    if (this.isEdit) {
      this.update();
    } else {
      this.user.status = "ACTIVE";
      this.save();
    }
  }

  save() {
    this.rentalService.saveUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data created successfully");
        this.cancel();
      },
      error: (error) => {
        this.toastr.error("User data failed to create");
      }
    });
  }

  getUserById() {
    this.rentalService.getUserById(this.userId).subscribe({
      next: (data) => {
        this.user = data.outputData.responseData;
        this.addressList = this.user.userAddress as any;
        this.convertUserToForm();
      },
      error: (error) => {
        this.toastr.error("Failed to get user by id");
      }
    });
  }

  update() {
    this.rentalService.updateUser(this.user).subscribe({
      next: (data) => {
        this.toastr.success("User data updated successfully");
        this.cancel();
      },
      error: (error) => {
        this.toastr.success("User data failed to update");
      }
    });
  }

  cancel() {
    this.router.navigateByUrl("/rental/user");
  }

}
