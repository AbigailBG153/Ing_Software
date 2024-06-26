import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable ,throwError } from 'rxjs';
import { ScheduleResponseDTO } from '../interfaces/meetingResponse.interface'
import { ScheduleRequestDTO } from '../interfaces/meetingRequest.interface';
@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private apiUrl = 'http://localhost:8080/ApiRest/v1';  
  constructor(private http: HttpClient) { }

  getDailySchedulesForCurrentWeek(specialistId: number): Observable<ScheduleResponseDTO[]> {
    return this.http.get<ScheduleResponseDTO[]>(`${this.apiUrl}/meetings/specialist/${specialistId}/currentWeek`).pipe(
      catchError(error => {
        console.error('Error en la solicitud HTTP:', error);
        return throwError(error); // Lanza el error para que lo maneje el componente que llama a este método
      })
    );
  }

  updateScheduleStatus(id: number, scheduleRequest: ScheduleRequestDTO): Observable<ScheduleResponseDTO> {
    return this.http.put<ScheduleResponseDTO>(`${this.apiUrl}/schedules/${id}`, scheduleRequest).pipe(
      catchError(error => {
        console.error('Error en la solicitud HTTP:', error);
        return throwError(error);
      })
    );
  }

}




