import { Pipe, PipeTransform } from '@angular/core';
import {MyListsResponse} from "../response/myListsResponse";

@Pipe({
  name: 'searchLists',
  standalone: true
})
export class SearchListsPipe implements PipeTransform {

  transform(lists: MyListsResponse[], searchFilter: string): MyListsResponse[] {
    return lists.filter(list => {
      return list.name!.toLocaleLowerCase().
      includes(searchFilter.toLocaleLowerCase());
    });
  }

}
