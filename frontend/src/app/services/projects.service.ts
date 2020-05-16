import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  getProperties() {
    throw new Error("Method not implemented.");
  }


  projects =[
    {
      name : 'mon super projet',
      description : 'un petit projet sympas'
    },
    {
      name : 'my super project',
      description : 'un small funny project'
    },
  ];

  projectsSubject = new Subject<any[]>();

  constructor() { }
  

  emitProjects(){
    this.projectsSubject.next(this.projects);
  }

  createProject(project){
    this.projects.push(project);
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
}
