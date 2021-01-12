import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './auth/login/login.component';
import {AuthGuard} from './auth/auth.guard';

const routes: Routes = [
  {
    path: 'principal',
    loadChildren: 'src/app/layout/layout.module#LayoutModule',
    canActivate: [AuthGuard],
    canLoad: [AuthGuard]
  },
  {path: '', pathMatch: 'full', redirectTo: '/login'},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
