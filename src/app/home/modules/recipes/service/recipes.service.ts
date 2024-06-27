import { HttpClient, HttpParams } from '@angular/common/http';
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
    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}`);
  }

  getRecipesByType(type: string): Observable<RecipeResponseDTO[]> {
    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}/by-type/${type}`);
  }

  getRecipesByNutritionalGoal(nutritionalGoal: string): Observable<RecipeResponseDTO[]> {
    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}/byNutritionalGoal/${nutritionalGoal}`);
  }

  getRecipesByCaloriesRange(minCalories: number, maxCalories: number): Observable<RecipeResponseDTO[]> {
    let params = new HttpParams()
      .set('minCalories', minCalories.toString())
      .set('maxCalories', maxCalories.toString());

    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}/calories`, { params: params });
  }

  getTopRatedRecipes(): Observable<RecipeResponseDTO[]> {
    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}/top-rated`);
  }

  getRecipesByTypeAndNutritionalGoal(type: string, nutritionalGoal: string): Observable<RecipeResponseDTO[]> {
    let params = new HttpParams()
      .set('type', type)
      .set('nutritionalGoal', nutritionalGoal);

    return this.http.get<RecipeResponseDTO[]>(`${this.apiUrl}/byTypeAndNutritionalGoal`, { params: params });
  }
}
