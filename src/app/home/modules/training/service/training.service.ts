import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TrainingResponseDTO } from '../interfaces/training.interface';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {

  private apiUrl = 'https://nutrilifeapi-d.onrender.com/ApiRest/v1/trainings';

  constructor(private http: HttpClient) { }

  getAllTrainings(): Observable<TrainingResponseDTO[]> {
    return this.http.get<TrainingResponseDTO[]>(`${this.apiUrl}`);
  }

  getTrainingsByType(type: string): Observable<TrainingResponseDTO[]> {
    return this.http.get<TrainingResponseDTO[]>(`${this.apiUrl}/type/${type}`);
  }

  getTrainingsByKcal(kcal: number): Observable<TrainingResponseDTO[]> {
    return this.http.get<TrainingResponseDTO[]>(`${this.apiUrl}/kcalories/${kcal}`);
  }

  getTrainingsByGoal(goal: string): Observable<TrainingResponseDTO[]> {
    return this.http.get<TrainingResponseDTO[]>(`${this.apiUrl}/goal/${goal}`);
  }

  getTrainingsByDuration(duration: number): Observable<TrainingResponseDTO[]> {
    return this.http.get<TrainingResponseDTO[]>(`${this.apiUrl}/duration/${duration}`);
  }
}
