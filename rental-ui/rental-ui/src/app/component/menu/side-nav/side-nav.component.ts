import { Component , HostListener } from '@angular/core';
import { NavItem } from '../../model/nav-item';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../../auth/auth-service';
import { MenuService } from '../menu-service';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.css'
})
export class SideNavComponent {

  //menu: NavItem[] = [];
  activeMenu = {}  as any;
  options: any;
  menuShortcutActive : boolean = false;
  profileShortcutActive : boolean = false;
  menuSidebarActive : boolean = false;
  menu: NavItem[] =[
  /*{
		route: "/dashboard",
		icon:"home",
		displayName: "Dashboard",
    items:[]
	}, {
		route: "/main/admin/product",
		displayName: "Products",
		icon:"dvr",
		items: []
	}, {
		route: "trading",
		displayName: "Trading",
		icon:"show_chart",
		items: []
	}, {
		route: "expesne",
		displayName: "Expense",
		icon:"account_balance_wallet",
		items: []
	}, {
		route: "people",
		displayName: "People",
		icon:"people_outline",
		items: []
	}, {
		route: "/main/admin/user",
		displayName: "User Management",
		icon:"person_outline",
		items: []
	}, {
		route: "report",
		displayName: "Reports",
		icon:"dashboard",
		items: []
	}, {
		route: "transfer",
		displayName: "Transfer",
		icon:"swap_horiz",
		items: []
	}, {
		route: "/warehouselist",
		icon:"domain",
		displayName: "Warehouse",
    items:[]
	}, {
		route: "/rolepermission",
		icon:"settings",
		displayName: "Administrative Tools",
    items:[]
	}*/
];


  constructor(private _formBuilder: FormBuilder, private menuService: MenuService, private authService: AuthService) {
    if (typeof window !== "undefined") {
      let path = window.location.pathname as any;
      this.menu.forEach(e => {
        if (path === e.route) {
          e.defaultSelected = true;
          this.activeMenu = e;
        } else {
          e.defaultSelected = false
        }
      })
    }
  }


  ngOnInit() {
    this.getAllMenu();
  }

  getAllMenu() {
    this.menuService.getAllMenu().subscribe({
      next: (data) => {
        this.menu = data.outputData.responseData;
        this.options = this._formBuilder.group({
          bottom: 0,
          fixed: false,
          top: 0,
        });
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  selectedMenu(menu: NavItem) {
    this.menu.forEach(e => {
      e.defaultSelected = false;
      if (menu.displayName.indexOf(e.displayName) != -1) {
        e.defaultSelected = true;
        this.activeMenu = e;
      }
    });
  }

  logout(){
    this.authService.logout();
  }

  shortmenu(){
    this.menuShortcutActive = !this.menuShortcutActive;
  }

  profilemenu(){
    this.profileShortcutActive = !this.profileShortcutActive;
  }

  sidemenuToggle(){
    this.menuSidebarActive = !this.menuSidebarActive;
  }
  @HostListener('document:click', ['$event'])
  handleClick(event: MouseEvent) {
    // Your click event handler logic here
   
  }

}
