import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipesRoutingModule } from './recipes-routing.module';
import { RecipesComponent } from './recipes.component';

import { MaterialModule } from '../../../material/material.module';
import { CapitalizeFirstPipe } from './pipes/capitalize-first.pipe';


@NgModule({
  declarations: [
    RecipesComponent,
    CapitalizeFirstPipe
  ],
  imports: [
    CommonModule,
    RecipesRoutingModule,
    MaterialModule
  ]
})
export class RecipesModule { }
