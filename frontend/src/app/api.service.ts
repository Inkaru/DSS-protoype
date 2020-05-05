import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

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
    return this.httpClient.get('api/projects/getAllProjects');
  }
}
