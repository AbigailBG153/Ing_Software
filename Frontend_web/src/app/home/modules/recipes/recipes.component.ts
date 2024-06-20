import { Component, OnInit } from '@angular/core';
import { RecipeResponseDTO } from './interfaces/recipes.interfaces';
import { RecipesService } from './service/recipes.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css'
})
export class RecipesComponent implements OnInit{
  recipes: RecipeResponseDTO[] = [];
  recipeRows: RecipeResponseDTO[][] = [];

  constructor(private recipeService: RecipesService) { }

  ngOnInit(): void {
    this.recipeService.getAllRecipes().subscribe(
      (data: RecipeResponseDTO[]) => {
        this.recipes = data;
        this.arrangeRecipesIntoRows();
      },
      (error) => {
        console.error('Error fetching recipes', error);
      }
    );

    
  }

  arrangeRecipesIntoRows(): void {
    const numberOfRows = Math.ceil(this.recipes.length / 3);
    for (let i = 0; i < numberOfRows; i++) {
      const start = i * 3;
      const end = start + 3;
      this.recipeRows.push(this.recipes.slice(start, end));
    }
  }
}
