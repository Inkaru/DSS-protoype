import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Project} from "../model/project";
import {User} from "../model/user";
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  //Project Methods

  // API calls are done with '/api/... '

  projects: Project[] = [];

  projectsSubject = new Subject<any[]>();

  public getAllProjects() {
    this.httpClient
      .get<any[]>('/api/projects/getAllProjects')
      .subscribe(
        (response) => {
          this.projects = response;
          this.emitProjects();
        },
        (error) => {
          console.log('Erreur ! ');
        }
      );
}

//https://my-json-server.typicode.com/inkaru/inkaru.github.io/companies

  // public getAllProjects(){
  //   return this.httpClient.get<Project[]>('/api/projects/getAllProjects');
  // }

  emitProjects(){
    this.projectsSubject.next(this.projects);
  }

//   createProject (project : Project) {
//     let params = new HttpParams().set('name',project.name);
//     params.set('description',project.description);
//     return this.http.post('api/projects/createProject', {params : params} );
//  }



  // add a new project in local
  /**createProject(project){
    this.projects.push(project);
  }**/

  /** POST: add a new project to the database **/
  createProject (project : Project) {
    let params = new HttpParams().set('name',project.name);
    params.set('description',project.description);
    return this.httpClient.post('api/projects/createProject', {params : params} );
 }



  getProjects() {}

  deleteProjects(index){
    this.projects.splice(index, 1);
    this.emitProjects();
  }

  updateProjects(project, index){
    this.projects[index] = project;
    this.emitProjects();
  }



  // User methods

  public getAllUsers(){
    return this.httpClient.get<Project[]>('/api/users/getAllUsers');
  }

  public getUserById(id: number){
    let params = new HttpParams().set('id',String(id));
    return this.httpClient.get<User>('/api/users/getUser', {params: params});
  }

  public getUserByLogin(login: string){
    let params = new HttpParams().set('loginName',login);
    return this.httpClient.get<User>('/api/users/getUser', {params: params});
  }

  public getUserByEmail(email: string){
    let params = new HttpParams().set('email',email);
    return this.httpClient.get<User>('/api/users/getUser', {params: params});
  }

  // TODO : test if it works
  public updateUser(user: User){
    return this.httpClient.post('/api/users/updateUser', JSON.stringify(user));
  }

  public deleteUserById(id: number){
    let params = new HttpParams().set('id',String(id));
    return this.httpClient.delete('/api/users/deleteUser', {params: params});
  }

  public deleteUserByLogin(login: string){
    let params = new HttpParams().set('loginName',login);
    return this.httpClient.delete('/api/users/deleteUser', {params: params});
  }

  public deleteUserByEmail(email: string){
    let params = new HttpParams().set('email',email);
    return this.httpClient.delete('/api/users/deleteUser', {params: params});
  }


}
