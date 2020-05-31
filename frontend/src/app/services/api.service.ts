import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Project} from '../model/project';
import {User} from '../model/user';
import {Subject} from 'rxjs';
import {MarketplaceItem} from '../model/marketplaceItem';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) {
  }

  projects: Project[] = [];
  projectsSubject = new Subject<Project[]>();

  mpItems: MarketplaceItem[] = [];
  mpItemsSubject = new Subject<MarketplaceItem[]>();

  // API calls are done with '/api/... '

  // Project Methods

  getAllProjects() {
    this.httpClient
      .get<any[]>('/api/projects/getAllProjects')
      .subscribe(
        (response) => {
          console.log('got projects from backend');
          console.log(response);
          this.projects = response;
          this.projectsSubject.next(this.projects);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  createProject(project: Project) {
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(project);
    return this.httpClient.post('/api/projects/createProject', data, {headers: header});
  }

  updateProject(project: Project) {
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(project);
    return this.httpClient.post('/api/projects/updateProject', data, {headers: header});
  }


  getProjectById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get<Project>('/api/projects/getProject', {params});
  }

  likeProject(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/likeProject', {params});
  }

  unlikeProject(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/unlikeProject', {params});
  }

  followProject(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/followProject', {params});
  }

  unfollowProject(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/unfollowProject', {params});
  }

  deleteProjectById(id) {
    const params = new HttpParams().set('id', id);
    return this.httpClient.delete('api/projects/deleteProject', {params});
  }

  // User methods

  getAllUsers() {
    return this.httpClient.get<Project[]>('/api/users/getAllUsers');
  }

  getUserById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  getUserByLogin(login: string) {
    const params = new HttpParams().set('loginName', login);
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  getUserByEmail(email: string) {
    const params = new HttpParams().set('email', email);
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  updateUser(user: User) {
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(user);
    return this.httpClient.post('/api/users/updateUser', data, {headers: header});
  }

  deleteUserById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  deleteUserByLogin(login: string) {
    const params = new HttpParams().set('loginName', login);
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  deleteUserByEmail(email: string) {
    const params = new HttpParams().set('email', email);
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  // Marketplace Methods

  createMPItem(item: MarketplaceItem) {
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(item);
    return this.httpClient.post('/api/marketplace/createItem', data, {headers: header});
  }

  updateMPItem(item: MarketplaceItem) {
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(item);
    return this.httpClient.post('/api/marketplace/updateItem', data, {headers: header});
  }

  deleteMPItemById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.delete('/api/marketplace/deleteItem', {params});
  }

  getMPItemById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get<MarketplaceItem>('/api/marketplace/getItem', {params});
  }

  getAllMPItems() {
    this.httpClient
      .get<any[]>('/api/marketplace/getAllItems')
      .subscribe(
        (response) => {
          console.log('got MPItems from backend');
          this.mpItems = response;
          this.mpItemsSubject.next(this.mpItems);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  getOffersMPItems() {
    this.httpClient
      .get<any[]>('/api/marketplace/getAllOffers')
      .subscribe(
        (response) => {
          console.log('got offers from backend');
          this.mpItems = response;
          this.mpItemsSubject.next(this.mpItems);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  getRequestsMPItems() {
    this.httpClient
      .get<any[]>('/api/marketplace/getAllRequests')
      .subscribe(
        (response) => {
          console.log('got requests from backend');
          this.mpItems = response;
          this.mpItemsSubject.next(this.mpItems);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  getUserRecommandation() {
    return this.httpClient.get<User[]>('/api/recommend/getUserRanking');
  }

  getProjectRecommandation() {
    return this.httpClient.get<Project[]>('/api/recommend/getProjectRanking');
  }

}
