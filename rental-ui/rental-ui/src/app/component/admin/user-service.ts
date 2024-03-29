import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';
import { User } from '../model/user';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  baseUrl: string = `${environment.apiUrl}rnt/`;

  constructor(private http: HttpClient) { }

  isLoggedIn() {
    const token = localStorage.getItem('token') as string;
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



}