import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Product } from '../../model/product';
import { ToastrService } from 'ngx-toastr';
import { RentalService } from '../rental-service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {

  private dataUrl: any ='assets/data/product.json';
  displayedColumns = ['id', 'image', 'name', 'code', 'category', 'subCategory', 'brand', 'unit', 'variant', 'stock','price','action'];
  dataSource!: MatTableDataSource<Product>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor( private rentalService: RentalService,  private toastr: ToastrService, private router: Router,private http: HttpClient) {

  }

  ngOnInit() {
    this.getAllProduct();
  }

  getAllProduct() {
    /*console.log(this.http.get<any>(this.dataUrl));
    return this.http.get<any>(this.dataUrl);*/
    this.rentalService.getAllProduct().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
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

  createProduct() {
    this.router.navigate(["/rental/add-product"]);
  }

  editProduct(data: Product){
    let url = "/rental/edit-user/"+ data.id as any;
    this.router.navigateByUrl(url);
  }

  delete(userId: number) {
    this.rentalService.deleteUserById(userId).subscribe({
      next: () => {
        this.toastr.success("User data deleted successfully");
        this.getAllProduct();
      },
      error: () => {
        this.toastr.error("User data failed to delete");
      }
    });
  }


}
