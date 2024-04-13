//import { Item } from "./item";
export interface NavItem {
    displayName: string;
    disabled?: boolean;
    icon: string;
    route?: string;
    defaultSelected?: boolean;
    items:Item[];
  }

export class Item {
    route?: string;
    displayName?: string;
    items?:ChildItem[];
}

export class ChildItem {
  route?: string;
  displayName?: string;
}