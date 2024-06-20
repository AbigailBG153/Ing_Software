import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SpecialistComponent } from './specialist.component';


const routes: Routes = [{ path: '', component: SpecialistComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SpecialistRoutingModule { }
