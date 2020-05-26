import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Project} from '../model/project';
import {User} from '../model/user';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  // Project Methods

  // API calls are done with '/api/... '

  projects: Project[] = [];

  projectsSubject = new Subject<any[]>();

  public getAllProjects() {
    this.httpClient
      .get<any[]>('/api/projects/getAllProjects')
      .subscribe(
        (response) => {
          console.log('got projects from backend');
          this.projects = response;
          this.emitProjects();
        },
        (error) => {
          console.log(error);
        }
      );
  }

  emitProjects(){
    this.projectsSubject.next(this.projects);
  }

  /* POST: add a new project to the database */

  createProject(project: Project) {
    // this.projects.push(project);
    // this.emitProjects();
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(project);
    return this.httpClient.post('/api/projects/createProject', data, {headers: header} );
 }

  public updateProject(project: Project){
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(project);
    return this.httpClient.post('/api/projects/updateProject', data, {headers: header} );
  }

  // User methods

  public getAllUsers(){
    return this.httpClient.get<Project[]>('/api/users/getAllUsers');
  }

  public getUserById(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  public getUserByLogin(login: string){
    const params = new HttpParams().set('loginName', login);
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  public getUserByEmail(email: string){
    const params = new HttpParams().set('email', email);
    return this.httpClient.get<User>('/api/users/getUser', {params});
  }

  public updateUser(user: User){
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(user);
    return this.httpClient.post('/api/users/updateUser', data, {headers: header} );
  }

  deleteUserById(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  deleteUserByLogin(login: string){
    const params = new HttpParams().set('loginName', login);
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  deleteUserByEmail(email: string){
    const params = new HttpParams().set('email', email);
    return this.httpClient.delete('/api/users/deleteUser', {params});
  }

  getProjectById(id) {
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get<Project>('/api/projects/getProject', {params});
  }

  likeProject(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/likeProject', {params});
  }

  unlikeProject(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/unlikeProject', {params});
  }

 followProject(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/followProject', {params});
  }

  unfollowProject(id){
    const params = new HttpParams().set('id', String(id));
    return this.httpClient.get('/api/users/unfollowProject', {params});
  }

  deleteProjectById(id) {
    const params = new HttpParams().set('id', id);
    return this.httpClient.delete('api/projects/deleteProject', {params});
  }
}
