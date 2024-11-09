import { Pipe, PipeTransform } from '@angular/core';
import {GetContactResponse} from "../../response/GetContactResponse";

@Pipe({
  name: 'searchContacts',
  standalone: true
})
export class SearchContactsPipe implements PipeTransform {

  transform(contacts: GetContactResponse[], searchFilter: string): GetContactResponse[] {
    const lowerCaseSearch = searchFilter.toLowerCase();

    return contacts.filter(contact => {
      const nameLower = contact.name?.toLowerCase() || '';
      const lastNameLower = contact.surname?.toLowerCase() || '';

      return (
        nameLower.includes(lowerCaseSearch) ||
        lastNameLower.includes(lowerCaseSearch)
      );
    });
  }


}
