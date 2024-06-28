import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SingupSpecialistRequest } from '../interfaces/singup-specialist-request';
import { SingupSpecialistResponse } from '../interfaces/singup-specialist-response';

@Injectable({
  providedIn: 'root'
})
export class SingUpSpecialistService {
  private apiUrl = 'https://nutrilifeapi-d.onrender.com/ApiRest/v1';  

  constructor(private http: HttpClient) { }

  signup(singupCustomerRequest : SingupSpecialistRequest) {
    const url = `${this.apiUrl}/specialists/singup-spec`;
    return this.http.post<SingupSpecialistResponse>(url, singupCustomerRequest);
  }
}
