import { Injectable } from '@angular/core';
import { HttpClient , HttpParams} from '@angular/common/http';
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

  getSpecialistsByOccupation(occupation: string): Observable<SpecialistResponseDTO[]> {
    return this.http.get<SpecialistResponseDTO[]>(`${this.apiUrl}/SearchByOccupation`,{
      params: {
        occupation: occupation
      }
  });
  }

  
  getSpecialistProfilesByAge(age: number): Observable<SpecialistResponseDTO[]> {
    return this.http.get<SpecialistResponseDTO[]>(`${this.apiUrl}/${age}`, {
      params: {
        age: age.toString()
      }
    });
  }

  getSpecialistProfilesByAgeRange(minAge: number, maxAge: number): Observable<SpecialistResponseDTO[]> {
    const params = new HttpParams()
      .set('minAge', minAge.toString())
      .set('maxAge', maxAge.toString());
    return this.http.get<SpecialistResponseDTO[]>(`${this.apiUrl}/by-age-range`, { params });
  } 

  getSpecialistProfilesByScore(score: number): Observable<SpecialistResponseDTO[]> {
    const params = new HttpParams().set('score', score.toString());
    return this.http.get<SpecialistResponseDTO[]>(`${this.apiUrl}/by-score`, { params });
  }
}
