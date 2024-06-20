import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { ListMeetingComponent } from './components/list-meeting/list-meeting.component';


@NgModule({
  declarations: [
    DashboardComponent,
    ListMeetingComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
