import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';
import { ProjectsComponent } from "./projects/projects.component";

const routes: Routes = [
  {path : 'home',component : HomeComponent},
  {path : 'admin/dashboard',component : AdminDashboardComponent},
  { path: 'projects', component: ProjectsComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
