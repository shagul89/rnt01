import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';
import { Configuration } from '../model/config';

@Injectable({
  providedIn: 'root',
})
export class RentalService {

  baseUrl: string = `${environment.apiUrl}rnt/`;

  constructor(private http: HttpClient) { }

  getAllCustomer(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-customer");
  }

  getCustomerById(customerId: number): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-by-customer-id/" + customerId);
  }

  saveCustomer(customer: any): Observable<any> {
    return this.http.post(this.baseUrl + "user/save-customer", customer);
  }

  updateCustomer(customer: any): Observable<any> {
    return this.http.put(this.baseUrl + "user/update-customer/" + customer.customerId, customer);
  }

  deleteCustomerById(customerId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "user/delete-customer-by-id/" + customerId);
  }

  getAllProduct(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-product");
  }

  getProductById(productId: number): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-by-product-id/" + productId);
  }

  saveProduct(product: any): Observable<any> {
    return this.http.post(this.baseUrl + "user/save-product", product);
  }

  updateProduct(product: any): Observable<any> {
    return this.http.put(this.baseUrl + "user/update-product/" + product.productId, product);
  }

  deleteProductById(productId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "user/delete-product-by-id/" + productId);
  }


  getAllStore(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-store");
  }

  getStoreById(storeId: number): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-by-store-id/" + storeId);
  }

  saveStore(store: any): Observable<any> {
    return this.http.post(this.baseUrl + "user/save-store", store);
  }

  updateStore(store: any): Observable<any> {
    return this.http.put(this.baseUrl + "user/update-store/" + store.storeId, store);
  }

  deleteStoreById(storeId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "user/delete-store-by-id/" + storeId);
  }


  getAllSupplier(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-supplier");
  }

  getSupplierById(supplierId: number): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-by-supplier-id/" + supplierId);
  }

  saveSupplier(supplier: any): Observable<any> {
    return this.http.post(this.baseUrl + "user/save-supplier", supplier);
  }

  updateSupplier(supplier: any): Observable<any> {
    return this.http.put(this.baseUrl + "user/update-supplier/" + supplier.supplierId, supplier);
  }

  deleteSupplierById(supplierId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "user/delete-supplier-by-id/" + supplierId);
  }

  getAllConfiguration(): Observable<any> {
    return this.http.get(this.baseUrl + "configuration/get-all-configuration");
  }

  getConfigurationById(configurationId: number): Observable<any> {
    return this.http.get(this.baseUrl + "configuration/get-by-configuration-id/" + configurationId);
  }

  saveConfiguration(configuration: Configuration): Observable<any> {
    return this.http.post(this.baseUrl + "configuration/save-configuration", configuration);
  }

  updateConfiguration(configuration: Configuration): Observable<any> {
    return this.http.put(this.baseUrl + "configuration/update-configuration/" + configuration.configId, configuration);
  }

  deleteConfigurationById(configurationId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "configuration/delete-configuration-by-id/" + configurationId);
  }

}