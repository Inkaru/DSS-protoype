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
      title : 'mon super projet',
      users : 'Florian neve',
      description : 'un petit projet sympas'
    },
    {
      title : 'my super projcet',
      users : 'Florian neve',
      description : 'un small funny project'
    },
  ];

  projectsSubject = new Subject<any[]>();

  constructor() { }
  

  emitProjects(){
    this.projectsSubject.next(this.projects);
  }

  creatProject(project){
    this.projects.push(project);
  }
  getProjects() {}

  deleteProjects(index){
    this.projects.splice(index, 1);
    this.emitProjects();
  }
}
