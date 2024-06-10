import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'specialist', loadChildren: () => import('./modules/specialist/specialist.module').then(m => m.SpecialistModule) },
  { path: 'dashboard', loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule) }, 
  { path: 'trainig', loadChildren: () => import('./modules/training/training.module').then(m => m.TrainingModule) }, 
  { path: 'recipes', loadChildren: () => import('./modules/recipes/recipes.module').then(m => m.RecipesModule) },
  {path: 'training', loadChildren: () => import('./modules/training/training.module').then(m => m.TrainingModule)}
];
  

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
