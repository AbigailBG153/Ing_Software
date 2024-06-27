import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthUserRoutingModule } from './auth-user-routing.module';
import { AuthUserComponent } from './auth-user.component';
import { BodyAuthComponent } from './shared/body-auth/body-auth.component';
import { HeaderAuthComponent } from './shared/header-auth/header-auth.component';


@NgModule({
  declarations: [
    AuthUserComponent,
    BodyAuthComponent,
    HeaderAuthComponent,
  ],
  imports: [
    CommonModule,
    AuthUserRoutingModule,
  ]
})
export class AuthUserModule { }
