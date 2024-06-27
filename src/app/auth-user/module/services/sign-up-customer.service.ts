import { SingupCustomerResponse } from './../interfaces/singup-customer-response';
import { SingupCustomerRequest } from './../interfaces/singup-customer-request';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignUpCustomerService {
  
  private apiUrl = 'http://localhost:8080/ApiRest/v1';  

  constructor(private http: HttpClient) { }

  signup(singupCustomerRequest : SingupCustomerRequest) {
    const url = `${this.apiUrl}/customers/singup-cust`;
    return this.http.post<SingupCustomerResponse>(url, singupCustomerRequest);
  }
}
