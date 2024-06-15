import { Component } from '@angular/core';

@Component({
  selector: 'app-add-edit-product-model',
  templateUrl: './add-edit-product-model.component.html',
  styleUrl: './add-edit-product-model.component.css'
})
export class AddEditProductModelComponent {
  isEdit = false;
  category = ['Laptop','Mobile','Electronics'];
  brand = ['Dell', 'Asus', 'Hp','Lenovo'];
  unit = ['Kilogram','Pieces','Meter'];
  subCategory=['Cloth','Bag','Food'];
}
