import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { ToastrService } from 'ngx-toastr';
import { RentalService } from '../rental-service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {

  displayedColumns = ['firstName', 'lastName', 'email', 'status', 'userType', 'createdBy', 'createdDate', 'updatedName', 'updatedDate', 'action'];
  dataSource!: MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor( private rentalService: RentalService,  private toastr: ToastrService, private router: Router,) {

  }

  ngOnInit() {
    this.getAllUser();
  }

  getAllUser() {
    this.rentalService.getAllUser().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data.outputData.responseData.dataList);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (error) => {
      }
    });
  }

  applyFilter(filterValue: any) {
    filterValue = filterValue.target.value;
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  createUser() {
    /*const dialogRef = this.dialog.open(AddEditUserModelComponent);

    dialogRef.afterClosed().subscribe(result => {
      
    });*/
    this.router.navigate(["/rental/addedituser"]);
  }

  editUser(data: User){
    //this.rentalService.inputAddData = data;
    this.router.navigate(["/rental/addedituser"]);
  }

  delete(userId: number) {
    this.rentalService.deleteUserById(userId).subscribe({
      next: () => {
        this.toastr.success("User data deleted successfully");
        this.getAllUser();
      },
      error: () => {
        this.toastr.error("User data failed to delete");
      }
    });
  }


}
