import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { User } from '../../model/user';
import { MatDialog } from '@angular/material/dialog';
import { AddEditUserModelComponent } from './add-edit-user-model/add-edit-user-model.component';
import { UserService } from '../user-service';
import { ToastrService } from 'ngx-toastr';

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

  constructor(public dialog: MatDialog, private userService: UserService,  private toastr: ToastrService) {

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
    const dialogRef = this.dialog.open(AddEditUserModelComponent);

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  editUser(data: User){
    const dialogRef = this.dialog.open(AddEditUserModelComponent, {
      data: {
        user : data
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
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
