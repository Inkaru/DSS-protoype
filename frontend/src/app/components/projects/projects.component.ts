import { Component, OnInit } from '@angular/core';
import { ApiService } from "../../services/api.service";

import{ProjectsService} from 'src/app/services/projects.service';
import {Project} from "../../model/project";
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  projects: Project[];
  projectsForm: FormGroup;
  projectsSubscription: Subscription;
  

  constructor(
    private apiService: ApiService,
    private formBuilder :FormBuilder,
    private projectsService : ProjectsService
    ) { }

  ngOnInit(): void {
    this.initProjectsForm();
    this.projectsService.emitProjects();
    // console.log(this.projects)
    // this.apiService.getAllProjects().subscribe((data) => {
    //   console.log(data);
    //   this.projects = data;
    // });
    this.projectsSubscription = this.projectsService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.projectsService.getProjects();
    this.projectsService.emitProjects();
    
  }

  slideConfig = {
    "slidesToShow": 1,
    "slidesToScoll": 1,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": true,
    "autoplay": true
  };

   initProjectsForm() {
    this.projectsForm = this.formBuilder.group({
      title: ['', Validators.required],
      users: ['', Validators.required],
      description: ''
    });
  }

  onSubmitProjectsForm(){
    const newProject = this.projectsForm.value;
    this.projectsService.creatProject(newProject);
    console.log(this.projectsService.projects)
  }

  ngOnDestroy() {
    this.projectsSubscription.unsubscribe();
  }

  resetForm(){
    this.projectsForm.reset();
  }

}
