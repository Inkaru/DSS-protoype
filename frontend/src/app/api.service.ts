import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Project} from "./project";

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
}
