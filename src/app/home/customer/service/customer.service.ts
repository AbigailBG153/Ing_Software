import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CustomerProfileService {

  private baseUrl = 'http://localhost:8080/ApiRest/v1/customers'; 

  constructor(private http: HttpClient) { }

  getAllProfiles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  getProfileById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getProfileByUserId(userId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/userid/${userId}`);
  }

  getProfileByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/name/${name}`);
  }

  findByDietType(dietType: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/dietType/${dietType}`);
  }

  createProfile(customerData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/signup-cust`, customerData);
  }

  deleteProfile(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}
