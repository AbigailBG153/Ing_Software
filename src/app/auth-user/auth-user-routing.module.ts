import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthUserComponent } from './auth-user.component';
const routes: Routes = [{
  path:'',
  component: AuthUserComponent,
  children: [
    { path: 'login', loadChildren: () => import('./module/login/login.module').then(m => m.LoginModule)},
    { path: 'choose-user', loadChildren: () => import('./module/choose-user/choose-user.module').then(m => m.ChooseUserModule)},
    { path: '', redirectTo: 'login', pathMatch: 'full' },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthUserRoutingModule { }
