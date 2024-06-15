import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';
import { Configuration } from '../model/config';
import { User } from '../model/user';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root',
})
export class RentalService {

  baseUrl: string = `${environment.apiUrl}rnt/`;

  constructor(private http: HttpClient) { }

  isLoggedIn() {
    const token = sessionStorage.getItem('token') as string;
    const payload = atob(token.split('.')[1]);
    const parsedPayload = JSON.parse(payload);
    return parsedPayload.exp > Date.now() / 1000;
  }

  getAllUser(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-user");
  }

  getUserById(userId: number): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-by-user-id/" + userId);
  }

  saveUser(user: User): Observable<any> {
    return this.http.post(this.baseUrl + "user/save-user", user);
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(this.baseUrl + "user/update-user/" + user.userId, user);
  }

  deleteUserById(userId: number): Observable<any> {
    return this.http.delete(this.baseUrl + "user/delete-user-by-id/" + userId);
  }

  login(user: Login): Observable<any> {
    return this.http.post(this.baseUrl + "auth/login", user);
  }

  getAllRolesPermission(): Observable<any> {
    return this.http.get(this.baseUrl + "user/get-all-roles-permission");
  }

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
    //return this.http.get(this.baseUrl + "user/get-all-product");
    return this.http.get("assets/data/product.json");
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