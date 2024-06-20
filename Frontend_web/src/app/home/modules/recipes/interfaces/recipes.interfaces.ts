
export interface IngredientRequestDTO {
    name: string;
    calories: number;
}


export interface IngredientResponseDTO {
    id: number;
    name: string;
    calories: number;
}


export interface RecipeRequestDTO {
    name: string;
    description: string;
    type: string;
    nutritionalGoal: string;
    totalCalories: number;
    image: string;
    ingredientIds: number[];
    score: number;
}


export interface RecipeResponseDTO {
    id: number;
    name: string;
    description: string;
    type: string;
    nutritionalGoal: string;
    totalCalories: number;
    image: string;
    ingredients: IngredientResponseDTO[];
    score: number;
}
