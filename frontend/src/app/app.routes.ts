import { Routes } from '@angular/router';
import {RegisterComponent} from "./component/RegisterComponent/register.component";
import {HomeComponent} from "./component/HomeComponent/home.component";
import {LoginComponent} from "./component/LoginComponent/login.component";
import {ListingComponent} from "./component/ListingComponent/listing.component";
import {AuthGuard} from "./guard/auth.guard";
import {ListDetailsComponent} from "./component/ListDetailsComponent/list-details.component";
import {ContactDetailsComponent} from "./component/ContactDetailsComponent/contact-details.component";

export const routes: Routes = [
  {
    path: '',
    title: 'Home',
    component: HomeComponent
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
    path: 'myLists',
    title: 'My lists',
    component: ListingComponent,
    canActivate: [AuthGuard]
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
  }
];
