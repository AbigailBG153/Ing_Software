import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TrainingRoutingModule } from './training-routing.module';
import { TrainingComponent } from './training.component';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    TrainingComponent
  ],
  imports: [
    CommonModule,
    TrainingRoutingModule,
    HttpClientModule
  ]
})
export class TrainingModule { }
