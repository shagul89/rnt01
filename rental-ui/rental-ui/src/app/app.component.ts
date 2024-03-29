
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MenuModule } from './component/menu/menu-module';
import { ToastrModule } from 'ngx-toastr';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AuthService } from './component/auth/auth-service';

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

  constructor(private authService: AuthService, private router: Router) {
    
  }

  isLogin(){
    if (typeof window !== "undefined") {
      if(this.authService.isLoggedIn()){
        return true;
      } 
    }
    return false;
  }
}
