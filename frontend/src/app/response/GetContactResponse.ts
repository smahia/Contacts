import {TelephoneResponse} from "./TelephoneResponse";
import {EmailResponse} from "./EmailResponse";
import {AddressResponse} from "./AddressResponse";

export class GetContactResponse {

  id?: number;
  name?: string;
  surname?: string;
  birthday?: string;
  contactEmergency?: boolean;
  telephoneList?: TelephoneResponse[];
  emailList?: EmailResponse[];
  addressesList?: AddressResponse[];
  listId?: number;

}
