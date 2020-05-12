import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {HomeComponent} from './components/home/home.component';
import {AdminDashboardComponent} from './components/admin/admin-dashboard/admin-dashboard.component';
import {ProjectsComponent} from "./components/projects/projects.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuard} from "./auth.gard";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'admin/dashboard', component: AdminDashboardComponent, canActivate: [AuthGuard]},
  {path: 'projects', component: ProjectsComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
