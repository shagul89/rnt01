import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';
import { Login } from '../model/login';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    baseUrl: string = `${environment.apiUrl}rnt/`;

    loggedIn = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient, private router: Router) { 
        if(typeof sessionStorage !== "undefined"){
            if(sessionStorage.getItem("token")){
                this.loggedIn.next(true);
            }
        }
    }

    logout() {
        if (typeof sessionStorage !== "undefined") {
            sessionStorage.removeItem("token");
            this.router.navigate(["/auth/login"]);
        }
    }

    get isLoggedIn() {
        return this.loggedIn.asObservable(); // {2}
    }

    login(user: Login): Observable<any> {
        return this.http.post(this.baseUrl + "auth/login", user);
    }

    refreshToken(token: any): Observable<any> {
        return this.http.post(this.baseUrl + "auth/refreshToken", token);
    }

}