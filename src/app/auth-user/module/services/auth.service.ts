import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';

import { catchError, map, Observable, throwError } from 'rxjs';
import { LoginRequest } from '../interfaces/login-request';
import { LoginResponse } from '../interfaces/login-response';

const authKey = 'banking_auth';
const userId = 'userId';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'https://nutrilifeapi-d.onrender.com/ApiRest/v1';
  private http = inject(HttpClient);
  private _auth = signal<LoginResponse | null>(null);

  auth = computed(() => this._auth());

  constructor() {
    const authString = localStorage.getItem(authKey);

    if (authString) {
      this._auth.set(JSON.parse(authString));
    }
  }

  login(authRequest: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/token`, authRequest)
      .pipe(
        map(response => {
          localStorage.setItem(authKey, JSON.stringify(response));
          localStorage.setItem(userId, response.user.userId.toString());
          this._auth.set(response);
          return response.user;
        })
      );
  }



  logout() {
  localStorage.removeItem(authKey);
  localStorage.removeItem(userId);
  //this._auth = undefined;
  this._auth.set(null);
  }



}