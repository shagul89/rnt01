import { Component } from '@angular/core';
import { Address } from '../../model/address';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-edit-address',
  templateUrl: './add-edit-address.component.html',
  styleUrl: './add-edit-address.component.css'
})
export class AddEditAddressComponent {


  displayedColumns: string[] = ['addressLine1', 'addressLine2', 'street', 'city', 'state', 'postalCode', 'country', 'mobileNumber', 'alternateMobileNumber', 'officeNumber', 'action'];
  dataSource: any;
  addressList: Address[] = [];
  isError = false;

  constructor(private toastr: ToastrService) {

  }

  ngOnInit() {

  }

  initAddress(list: Address[]){
    this.addressList = list ? list : [];
    for(var i=0;i<this.addressList.length;i++){
      this.addressList[i].index = i;
    }
    this.dataSource = new MatTableDataSource(this.addressList);
  }

  createAddress() {
    let addAddress = {} as Address;
    this.addressList.push(addAddress);
    for(var i=0;i<this.addressList.length;i++){
      this.addressList[i].index = i;
    }
    this.dataSource = new MatTableDataSource(this.addressList);
  }

  addAddress() {
    this.validateAddress();
    if (this.addressList.length > 0 && !this.isError) {
      this.createAddress();
    } else if (this.addressList.length == 0) {
      this.createAddress();
    }
  }

  deleteAddress(index: number) {
    this.addressList = this.addressList.filter(e => e.index != index);
    this.dataSource = new MatTableDataSource(this.addressList);
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

}
