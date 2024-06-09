import { Component, Input, ViewEncapsulation } from '@angular/core';
import { Address } from '../../../model/address';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-edit-address',
  templateUrl: './add-edit-address.component.html',
  styleUrl: './add-edit-address.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AddEditAddressComponent {

  @Input() addressList: Address[] = [];
  isError = false;
  @Input() isEdit = false;
  isChecked :boolean =false;
  isDisabled :boolean =false;

  constructor(private toastr: ToastrService) {

  }

  ngOnInit() {
    if (!this.isEdit) {
      this.setDefaultAdddress();
    } else{
      if(this.addressList.length ==0){
        this.setDefaultAdddress();
      }
    }
  }

  setDefaultAdddress() {
    let permanent = {} as Address;
    permanent.addressType = "PERMANENT";

    let temporary = {} as Address;
    temporary.addressType = "TEMPORARY";

    this.addressList.push(permanent);
    this.addressList.push(temporary);
  }

  validateAddress() {
    this.isError = false;
    this.addressList.forEach(e => {
      if (!e.addressLine1 || !e.street || !e.city || !e.state || !e.postalCode || !e.country || !e.mobileNumber || !e.alternateMobileNumber) {
        this.isError = true;
      }
    });
    if (this.isError) {
      this.toastr.error("Please add all the mandatory address fields");
    }
  }

  checkboxcChanged(){
    if(this.isChecked === true){
      if(this.addressList[0].addressType ==="PERMANENT"){
        let copiedObj = {...this.addressList[0]};
        if(this.addressList[1].addressType ==="TEMPORARY"){
         this.addressList[1]=copiedObj;
         this.addressList[1].addressType="TEMPORARY";
        }
      }
    }else{
      if(this.addressList[1].addressType ==="TEMPORARY"){
        let temporary = {} as Address;
        temporary.addressType = "TEMPORARY";
        this.addressList[1] =temporary;
       }
    }
  }

}
