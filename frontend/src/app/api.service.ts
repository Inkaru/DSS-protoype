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
  public registerUser(loginName: string, email: string, password: string, passwordRepeat: string){
    let params = new HttpParams().set('loginName',loginName);
    params.set('email',email);
    params.set('password',password);
    params.set('passwordRepeat',passwordRepeat);
    return this.httpClient.post('/api/users/registerUser',{params: params});
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
