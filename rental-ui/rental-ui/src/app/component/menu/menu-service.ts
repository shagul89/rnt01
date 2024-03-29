import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/envornment';

@Injectable({
  providedIn: 'root',
})
export class MenuService {

  baseUrl: string = `${environment.apiUrl}rnt/`;

  constructor(private http: HttpClient) { }


  getAllMenu(): Observable<any> {
    return this.http.get(this.baseUrl + "menu/get-all-menu");
  }



}