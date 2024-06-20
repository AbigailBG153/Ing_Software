import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SpecialistRoutingModule } from './specialist-routing.module';
import { SpecialistComponent } from './specialist.component';
import { ScheduleComponent } from './components/schedule/schedule.component';
import { MeetingComponent } from './components/meeting/meeting.component';

@NgModule({
  declarations: [
    SpecialistComponent,
    ScheduleComponent,
    MeetingComponent
  ],
  imports: [
    CommonModule,
    SpecialistRoutingModule
  ]
})
export class SpecialistModule { }
