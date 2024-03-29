import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'removeSpecialChar'
})
export class RemoveSpecialCharPipe implements PipeTransform {

  transform(value: string): string {
    return value ? value.replace(/[^a-zA-Z ]/g, " ") : value;
  }

}