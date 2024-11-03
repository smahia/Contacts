import { Routes } from '@angular/router';
import {RegisterComponent} from "./component/RegisterComponent/register.component";
import {LoginComponent} from "./component/LoginComponent/login.component";
import {ListingComponent} from "./component/ListingComponent/listing.component";
import {AuthGuard} from "./guard/auth.guard";
import {ListDetailsComponent} from "./component/ListDetailsComponent/list-details.component";
import {ContactDetailsComponent} from "./component/ContactDetailsComponent/contact-details.component";
import {AddContactComponent} from "./component/AddContactComponent/add-contact.component";
import {ChangePasswordComponent} from "./component/ChangePassword/change-password.component";

export const routes: Routes = [
  {
    path: '',
    title: 'My lists',
    component: ListingComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'registration',
    title: 'Registration',
    component: RegisterComponent
  },
  {
    path: 'login',
    title: 'Log in',
    component: LoginComponent
  },
  {
    path: 'list/:id', // TODO: check if the id if not a number or doesn't exist with a Guard
    component: ListDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'contact/:id', // TODO: check if the id if not a number or doesn't exist with a Guard
    component: ContactDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'newContact/:listId', // TODO: check if the id if not a number or doesn't exist with a Guard
    title: 'Create a new contact',
    component: AddContactComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'changePassword',
    title: 'Change password',
    component: ChangePasswordComponent,
    canActivate: [AuthGuard]
  }
];
