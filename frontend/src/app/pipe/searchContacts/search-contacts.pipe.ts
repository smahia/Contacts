import { Pipe, PipeTransform } from '@angular/core';
import {GetContactResponse} from "../../response/GetContactResponse";

@Pipe({
  name: 'searchContacts',
  standalone: true
})
export class SearchContactsPipe implements PipeTransform {

  transform(contacts: GetContactResponse[], searchFilter: string): GetContactResponse[] {
    const lowerCaseSearch = searchFilter.toLowerCase();

    // If the input is empty (the user does not search anything) then return the whole array of contacts
    // that way the filter of slide for the paginator applies to the contacts array and not to the search
    if (lowerCaseSearch !== '') {
      return contacts.filter(contact => {
        const nameLower = contact.name?.toLowerCase() || '';
        const lastNameLower = contact.surname?.toLowerCase() || '';

        return (
          nameLower.includes(lowerCaseSearch) ||
          lastNameLower.includes(lowerCaseSearch)
        );
      });
    } else {
      return contacts;
    }

  }


}
