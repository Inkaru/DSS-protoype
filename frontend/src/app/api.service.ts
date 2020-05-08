import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Project} from "./project";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  // API calls are done with '/api/... '

  public getFakeProjects(){
    return this.httpClient.get('https://my-json-server.typicode.com/inkaru/inkaru.github.io/companies');
  }

  public getAllProjects(){
    return this.httpClient.get<Project[]>('/api/projects/getAllProjects');
  }

  public getAllUsers(){
    return this.httpClient.get<Project[]>('/api/users/getAllUsers');
  }


  // TODO : make getUser methods work
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

  public registerUser(user: User){
    return this.httpClient.post('/api/users/registerUser',JSON.stringify(user));
  }

  public updateUser(user: User){
    return this.httpClient.post('/api/users/updateUser', JSON.stringify(user));
  }

  // TODO : add delete method


}
