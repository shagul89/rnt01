import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SideNavComponent } from './side-nav/side-nav.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MainContentComponent } from './main-content/main-content.component';
import { MainRoutingModule } from './menu-routes';

@NgModule({
  imports: [
    CommonModule,
    MatSidenavModule,
    MatSlideToggleModule,
    RouterModule,
    MatIconModule,
    MatListModule,
    MatToolbarModule,
    MatButtonModule,
    MainRoutingModule
  ],
  declarations: [
    SideNavComponent,
    MainContentComponent
  ],
  exports: [
    SideNavComponent,
    MainContentComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [
  ]
})
export class MenuModule { }