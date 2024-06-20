import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpecialistResponseDTO } from '../interfaces/meetingResponse.interface'
@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrl = 'http://localhost:8080/ApiRest/v1/specialists';  

  constructor(private http: HttpClient) { }

  getAllSpecialist(): Observable<SpecialistResponseDTO[]> {
    return this.http.get<SpecialistResponseDTO[]>(`${this.apiUrl}`);
  }

  getSpecialistProfileById(id: number): Observable<SpecialistResponseDTO> {
    return this.http.get<SpecialistResponseDTO>(`${this.apiUrl}/SearchById/${id}`);
  }
}
