import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Login } from '../../model/login';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../auth-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  form: any;
  login: Login = new Login();

  constructor(private formBuilder: FormBuilder, private authsService: AuthService,
    private router: Router, private toastr: ToastrService) {

  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.maxLength(10), Validators.minLength(8)]]
    })
  }

  get f() {
    return this.form.controls;
  }

  signIn() {
    let login = {
      username: this.form.get('userName').value,
      password: this.form.get('password').value
    } as Login;
    this.authsService.login(login).subscribe({
      next: (data) => {
        if (typeof sessionStorage !== "undefined") {
          let token = data.token;
          this.refreshToken(token);
        }
      },
      error: (error) => {
        this.toastr.success('Login Failed');
      }
    });
  }

  refreshToken(token: string) {
    let login = {
      token: token
    } as any;
    this.authsService.refreshToken(login).subscribe({
      next: (data) => {
        if (typeof sessionStorage !== "undefined") {
          sessionStorage.setItem('token', data.accessToken);
          this.toastr.success('Login Successfully');
          this.router.navigate(["/main/rental/dashboard"]);
        }
      },
      error: (error) => {
        this.toastr.success('Login Failed');
      }
    });
  }

}
