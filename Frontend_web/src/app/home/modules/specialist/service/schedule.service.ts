import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ScheduleResponseDTO } from '../interfaces/meeting.interface'
@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private apiUrl = 'http://localhost:8080/ApiRest/v1/meetings/specialist';  

  constructor(private http: HttpClient) { }

  getDailySchedulesForCurrentWeek(specialistId: number): Observable<ScheduleResponseDTO[]> {
    return this.http.get<ScheduleResponseDTO[]>(`${this.apiUrl}/${specialistId}/currentWeek`);
  }

}


