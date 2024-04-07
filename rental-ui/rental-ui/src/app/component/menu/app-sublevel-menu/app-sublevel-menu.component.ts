import { Component, Input } from '@angular/core';
import { Item } from "../../model/nav-item";
@Component({
  selector: 'app-sublevel-menu',
  templateUrl: './app-sublevel-menu.component.html',
  styleUrl: './app-sublevel-menu.component.css'
})
export class AppSublevelMenuComponent {
  @Input() subMenu? : Item[];
  constructor(){
  }
  ngOnInit() {
  console.log("SubMenu list****"+ this.subMenu);
  }
}
