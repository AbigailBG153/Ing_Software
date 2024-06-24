import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule  } from '@angular/forms';

import { RecipesRoutingModule } from './recipes-routing.module';
import { RecipesComponent } from './recipes.component';

import { MaterialModule } from '../../../material/material.module';
import { CapitalizeFirstPipe } from './pipes/capitalize-first.pipe';
import { MatSliderModule } from '@angular/material/slider';


@NgModule({
  declarations: [
    RecipesComponent,
    CapitalizeFirstPipe,
  ],
  imports: [
    CommonModule,
    RecipesRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule
  ]
})
export class RecipesModule { }
