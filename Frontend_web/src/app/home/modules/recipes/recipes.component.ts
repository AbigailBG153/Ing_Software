import { Component, OnInit } from '@angular/core';
import { RecipeResponseDTO } from './interfaces/recipes.interfaces';
import { RecipesService } from './service/recipes.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {
  recipes: RecipeResponseDTO[] = [];
  recipeRows: RecipeResponseDTO[][] = [];
  selectedType: string = '';
  selectedGoal: string = '';
  caloriesRange: number[] = [0, 1000];
  showTopRated: boolean = false;

  constructor(private recipeService: RecipesService) { }

  ngOnInit(): void {
    this.fetchRecipes(); // Fetch recipes initially
  }

  fetchRecipes(): void {
    this.recipeService.getAllRecipes().subscribe(
      (data: RecipeResponseDTO[]) => {
        this.recipes = data;
        if (!this.showTopRated) {
          this.filterRecipes(); // Apply existing filters only if not showing top rated
        }
      },
      (error) => {
        console.error('Error fetching recipes', error);
      }
    );
  }

  toggleTopRatedRecipes(): void {
    this.showTopRated = !this.showTopRated;
    if (this.showTopRated) {
      this.loadTopRatedRecipes(); // Load top rated recipes if toggle is on
    } else {
      this.fetchRecipes(); // Otherwise, apply filters
    }
  }

  loadTopRatedRecipes(): void {
    this.recipeService.getTopRatedRecipes().subscribe(
      (data: RecipeResponseDTO[]) => {
        this.recipes = data;
        this.arrangeRecipesIntoRows(this.recipes); // Arrange top rated recipes
      },
      (error) => {
        console.error('Error fetching top rated recipes', error);
      }
    );
  }

  filterRecipes(): void {
    let filteredRecipes = this.recipes.slice();

    if (this.selectedType) {
      this.recipeService.getRecipesByType(this.selectedType).subscribe(
        (data: RecipeResponseDTO[]) => {
          filteredRecipes = data;
          this.applyAdditionalFilters(filteredRecipes);
        },
        (error) => {
          console.error('Error fetching recipes by type', error);
        }
      );
    } else {
      this.applyAdditionalFilters(filteredRecipes);
    }
  }

  onFilterButtonClicked(): void {
    // Fetch and filter recipes based on selected filters
    this.filterRecipes();
  }
  
  applyAdditionalFilters(filteredRecipes: RecipeResponseDTO[]): void {
    if (this.selectedGoal) {
      filteredRecipes = filteredRecipes.filter(recipe => recipe.nutritionalGoal === this.selectedGoal);
    }

    if (this.caloriesRange[0] >= 0 && this.caloriesRange[1] <= 1000) {
      this.recipeService.getRecipesByCaloriesRange(this.caloriesRange[0], this.caloriesRange[1]).subscribe(
        (data: RecipeResponseDTO[]) => {
          filteredRecipes = filteredRecipes.filter(recipe =>
            recipe.totalCalories !== null && recipe.totalCalories !== undefined &&
            recipe.totalCalories >= this.caloriesRange[0] && recipe.totalCalories <= this.caloriesRange[1]);
          this.arrangeRecipesIntoRows(filteredRecipes); // Update the displayed recipes
        },
        (error) => {
          console.error('Error fetching recipes by calories range', error);
        }
      );
    } else {
      this.arrangeRecipesIntoRows(filteredRecipes); // Update the displayed recipes
    }
  }

  arrangeRecipesIntoRows(recipes: RecipeResponseDTO[]): void {
    const numberOfRows = Math.ceil(recipes.length / 3);
    this.recipeRows = [];
    for (let i = 0; i < numberOfRows; i++) {
      const start = i * 3;
      const end = start + 3;
      this.recipeRows.push(recipes.slice(start, end));
    }
  }
  
  getThumbPosition(value: number): string {
    const percent = (value - 0) / (1000 - 0) * 100; // Adjust based on your slider's min and max range
    return `${percent}%`;
  }

  handleStartThumbInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target) {
      this.caloriesRange[0] = +target.valueAsNumber;
    }
  }
  
  handleEndThumbInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target) {
      this.caloriesRange[1] = +target.valueAsNumber;
    }
  }
}
