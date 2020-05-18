import { Component, OnInit } from '@angular/core';
import { ApiService } from "../../services/api.service";

// import{ProjectsService} from 'src/app/services/projects.service';
import {Project} from "../../model/project";
import { NgForm, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import * as bootstrap from 'bootstrap';
import * as $AB from 'jquery';

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
    // private projectsService : ProjectsService
    ) { }

  ngOnInit(): void {
    this.initProjectsForm();
    // console.log(this.projects)
    // this.apiService.getAllProjects().subscribe((data) => {
    //   console.log(data);
    //   this.projects = data;
    // });
    this.projectsSubscription = this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
    this.apiService.emitProjects();

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
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmitProjectsForm(){
    const newProject = this.projectsForm.value;
    if (this.editMode){
      this.apiService.updateProjects(newProject, this.indexToUpdate);
    }else{
      this.apiService.createProject(newProject);
    }
    $('#projectsFormModal').modal('hide');
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
      this.apiService.deleteProjects(index);
    }
  }

  onEditProject(project){
    this.editMode = true;
    $('#projectsFormModal').modal('show');

    this.projectsForm.get('name').setValue(project.name);
    this.projectsForm.get('description').setValue(project.description);
    const index = this.projects.findIndex(
      (projectEl) => {
        if (projectEl === project) {
          return true;
        }
      }
    );
    this.indexToUpdate = index;


  }

}
