import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Configuration } from '../../model/config';
import { AddEditConfigModelComponent } from './add-edit-config-model/add-edit-config-model.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { RentalService } from '../rental-service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})
export class ConfigurationComponent {

  displayedColumns = ['name', 'value', 'description', 'createdBy', 'createdDate', 'updatedName', 'updatedDate', 'action'];
  dataSource!: MatTableDataSource<Configuration>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private rental: RentalService, private toastr: ToastrService) {

  }

  ngOnInit() {
    this.getAllConfiguration();
  }

  delete(configId: number){
    this.rental.deleteConfigurationById(configId).subscribe({
      next: (data) => {
        this.toastr.success("Configuration data deleted successfully");
      },
      error: () => {
        this.toastr.error("Configuration data failed to delete");
      }
    });
  }

  applyFilter(filterValue: any) {
    filterValue = filterValue.target.value;
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  addOrUpdateConfiguration(title: string, config: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      title : title,
      config : config
    }
    const dialogRef = this.dialog.open(AddEditConfigModelComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if(result && result.event === 'success'){
        this.getAllConfiguration();
      }
    });
  }

  getAllConfiguration(){
    this.rental.getAllConfiguration().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data.outputData.responseData);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: () => {
      }
    });
  }

}
