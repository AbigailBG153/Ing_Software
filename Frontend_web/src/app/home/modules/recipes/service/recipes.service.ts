import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RecipeResponseDTO } from '../interfaces/recipes.interfaces';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private apiUrl = 'http://localhost:8080/ApiRest/v1/recipes';  

  constructor(private http: HttpClient) { }

  getAllRecipes(): Observable<RecipeResponseDTO[]> {
    return this.http.get<RecipeResponseDTO[]>(this.apiUrl);
  }
}
