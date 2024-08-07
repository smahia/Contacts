import {NewTelephoneRequest} from "./NewTelephoneRequest";
import {NewEmailRequest} from "./NewEmailRequest";
import {NewAddressRequest} from "./NewAddressRequest";

export class NewContactRequest {

  name?: string;
  surname?: string;
  birthday?: string;
  contactEmergency: boolean = false;
  telephoneList: NewTelephoneRequest[] = [];
  emailList: NewEmailRequest[] = [];
  addressesList: NewAddressRequest[] = [];

}
