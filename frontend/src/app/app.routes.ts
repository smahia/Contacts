import { Routes } from '@angular/router';
import {RegisterComponent} from "./component/RegisterComponent/register.component";
import {HomeComponent} from "./component/HomeComponent/home.component";
import {LoginComponent} from "./component/LoginComponent/login.component";

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
  }
];
