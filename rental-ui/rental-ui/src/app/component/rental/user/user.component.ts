import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { UserService } from '../../rental/user-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit{

  displayedColumns = ['firstName', 'lastName', 'email', 'status', 'userType', 'createdBy', 'createdDate', 'updatedName', 'updatedDate', 'action'];
  dataSource!: MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor( private userService: UserService,  private toastr: ToastrService, private router: Router,) {

  }

  ngOnInit() {
    this.getAllUser();
  }

  getAllUser() {
    this.userService.getAllUser().subscribe({
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
    this.router.navigate(["/main/admin/addedituser"]);
  }

  editUser(data: User){
    this.userService.inputAddData = data;
    this.router.navigate(["/main/admin/addedituser"]);
    /*const dialogRef = this.dialog.open(AddEditUserModelComponent, {
      data: {
        user : data
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });*/
  }

  delete(userId: number) {
    this.userService.deleteUserById(userId).subscribe({
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
