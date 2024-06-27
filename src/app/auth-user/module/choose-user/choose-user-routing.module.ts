import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChooseUserComponent } from './choose-user.component';
const routes: Routes = [{ path: '', component: ChooseUserComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChooseUserRoutingModule { }
