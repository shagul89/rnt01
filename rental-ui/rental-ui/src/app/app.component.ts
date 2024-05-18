
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuModule } from './component/menu/menu-module';
import { ToastrModule } from 'ngx-toastr';
import { NgxSpinnerModule } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { AuthService } from './component/rental/auth-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MenuModule, ToastrModule, NgxSpinnerModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  title = 'rental-app-ui';
  isLoggedIn!: Observable<boolean>;

  constructor(private authService: AuthService, private router: Router) {
    this.isLoggedIn = this.authService.isLoggedIn;
  }

}
