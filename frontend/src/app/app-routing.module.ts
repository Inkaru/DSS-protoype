import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {HomeComponent} from './components/home/home.component';
import {ProjectsComponent} from './components/projects/projects.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './auth.gard';
import {ProfileComponent} from './components/profile/profile.component';
import {ProjectDetailComponent} from './components/project-detail/project-detail.component';
import {ResourcesComponent} from './components/resources/resources.component';
import {MarketplaceComponent} from './components/marketplace/marketplace.component';
import {MarketplaceDetailComponent} from './components/marketplace-detail/marketplace-detail.component';
import {ChatComponent} from './components/chat/chat.component';
import {UserDetailComponent} from './components/user-detail/user-detail.component';
import {SuggestionsComponent} from './components/suggestions/suggestions.component';


const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'projects', component: ProjectsComponent, canActivate: [AuthGuard]},
  {path: 'project/:id', component: ProjectDetailComponent, canActivate: [AuthGuard]},
  {path: 'marketplaceItem/:id', component: MarketplaceDetailComponent, canActivate: [AuthGuard]},
  {path: 'user/:id', component: UserDetailComponent, canActivate: [AuthGuard]},
  {path: 'marketplace', component: MarketplaceComponent, canActivate: [AuthGuard]},
  {path: 'resources', component: ResourcesComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'chat', component: ChatComponent, canActivate: [AuthGuard]},
  {path: 'suggestions', component: SuggestionsComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
