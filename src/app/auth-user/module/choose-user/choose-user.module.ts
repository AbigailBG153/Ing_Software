import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChooseUserRoutingModule } from './choose-user-routing.module';
import { ChooseUserComponent } from './choose-user.component';

import { ReactiveFormsModule } from '@angular/forms';

import { RegisterCustomerComponent } from '../register-customer/register-customer.component';
import { RegisterSpecialistComponent } from '../register-specialist/register-specialist.component';
@NgModule({
  declarations: [
    ChooseUserComponent,
    RegisterCustomerComponent,
    RegisterSpecialistComponent
  ],
  imports: [
    CommonModule,
    ChooseUserRoutingModule,
    ReactiveFormsModule
  ],
  bootstrap: [ChooseUserComponent]
})
export class ChooseUserModule { }
