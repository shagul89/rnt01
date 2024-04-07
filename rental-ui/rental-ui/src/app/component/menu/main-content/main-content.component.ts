import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrl: './main-content.component.css'
})
export class MainContentComponent {

  @Input() title: string = 'Dashboard';

  constructor(){
  }
  ngOninit(){
    console.log("#########"+this.title);
  }
}
