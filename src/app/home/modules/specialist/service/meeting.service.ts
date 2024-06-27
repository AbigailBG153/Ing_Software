import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable ,throwError } from 'rxjs';
import { MeetingResponseDTO  } from '../interfaces/meetingResponse.interface';
import { MeetingRequestDTO } from '../interfaces/meetingRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {

  private apiUrl = 'http://localhost:8080/ApiRest/v1/meetings';  

  constructor(private http: HttpClient) { }

  createMeeting(scheduleId: number, customerId: number): Observable<MeetingResponseDTO> {
    return this.http.post<MeetingResponseDTO>(`${this.apiUrl}/customer/${customerId}`, null, {
        params: {
            scheduleId: scheduleId.toString()
        }
    });
  }
  getMeetingsByCustomerId(customerId: number): Observable<MeetingResponseDTO[]> {
    return this.http.get<MeetingResponseDTO[]>(`${this.apiUrl}/customer/${customerId}`);
  }
  
  updateMeeting(meetingId: number, meetingRequest: MeetingRequestDTO): Observable<MeetingResponseDTO> {
    return this.http.put<MeetingResponseDTO>(`${this.apiUrl}`, meetingRequest , {
      params:{
        meetingId : meetingId.toString()
      }
    });
  }

}
