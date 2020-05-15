import { Component, OnInit } from '@angular/core';
import { ApiService } from "../../services/api.service";

import{ProjectsService} from 'src/app/services/projects.service';
import {Project} from "../../model/project";
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import * as $ from 'jquery';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  projects: Project[];
  projectsForm: FormGroup;
  projectsSubscription: Subscription;
  
  indexToUpdate;
  editMode = false;

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
    if (this.editMode){
      this.projectsService.updateProjects(newProject, this.indexToUpdate);
    }else{
      this.projectsService.createProject(newProject);
    }
    $('#projectsForm').modal('hide');
  }

  ngOnDestroy() {
    this.projectsSubscription.unsubscribe();
  }

  resetForm(){
    this.editMode = false;
    this.projectsForm.reset();
  }

  onDeleteProject(index){
    if (confirm("Are you sure you want delete this project?")){
      this.projectsService.deleteProjects(index);
    } 
  }

  onEditProject(project){
    this.editMode = true;
    $('#projectsForm').modal('show');
    this.projectsForm.get('title').setValue(project.title);
    this.projectsForm.get('users').setValue(project.users);
    this.projectsForm.get('description').setValue(project.description);
    const index = this.projects.findIndex(
      (projectEl)=>{
        if (projectEl === project){
          return true;
        }
      }
    );
    this.indexToUpdate = index;
    
  }

}
