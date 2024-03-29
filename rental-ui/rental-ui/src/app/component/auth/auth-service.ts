import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';
import { Login } from '../model/login';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    baseUrl: string = `${environment.apiUrl}rnt/`;

    constructor(private http: HttpClient, private router: Router) { }

    logout(){
        if(typeof localStorage !== "undefined"){
          localStorage.removeItem("token");
          this.router.navigate(["/auth/login"]);
        }
      }

    isLoggedIn() {
        if(typeof localStorage !== "undefined"){
            const token = localStorage.getItem('token') as string;
            return token as string ? true : false;
        }
        return false;
    }

    login(user: Login): Observable<any> {
        return this.http.post(this.baseUrl + "auth/login", user);
    }

    refreshToken(token: any): Observable<any> {
        return this.http.post(this.baseUrl + "auth/refreshToken", token);
    }

}