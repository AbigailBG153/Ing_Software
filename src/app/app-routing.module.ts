import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'home', loadChildren: () => import('./home/home.module').then(m => m.HomeModule) },
  { path: 'auth-user', loadChildren: () => import('./auth-user/auth-user.module').then(m => m.AuthUserModule) },
  { path: '', redirectTo: '/auth-user', pathMatch: 'full' },
  //{ path: '', redirectTo: '/home', pathMatch: 'full' },
];
  

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
