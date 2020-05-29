import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  constructor(
    private apiService: ApiService,
    private formBuilder: FormBuilder
  ) {
    this.projectsForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  error: string;

  currentProject: Project = new Project();
  tag = '';
  projects: Project[];

  projectsForm: FormGroup;

  slideConfig = {
    slidesToShow: 1,
    slidesToScoll: 1,
    nextArrow: '<div class=\'nav-btn next-slide\'></div>',
    prevArrow: '<div class=\'nav-btn prev-slide\'></div>',
    dots: true,
    autoplay: true
  };

  ngOnInit() {
    this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
  }

  addTag(){
    if (this.tag !== '' ){
      this.currentProject.hashTags.push('#' + this.tag);
      this.tag = '';
    }
  }

  removeTag(tag: string) {
    if (tag !== '' ){
      const index = this.currentProject.hashTags.indexOf(tag);
      console.log(tag);
      console.log(index);
      if (index !== -1){
        this.currentProject.hashTags.splice(index, 1);
      }
    }
  }

  onSubmitProjectsForm() {
    this.apiService.createProject(this.currentProject).subscribe(data => {
        console.log('success');
        console.log(data);
        this.apiService.getAllProjects();
        this.currentProject = new Project();
        // @ts-ignore
        $('#projectFormModal').modal('hide');
      },
      error => {
        console.log(error);
        this.error = error.error.message;
      });
  }

  // onSubmitProjectsForm() {
  //   if (this.projectsForm.invalid){
  //     this.error = 'All fields must be filled';
  //     return;
  //   }
  //   this.apiService.createProject(this.projectsForm.value).subscribe(
  //     response => {
  //       console.log(response);
  //       this.apiService.getAllProjects();
  //       // @ts-ignore
  //       $('#projectsFormModal').modal('hide');
  //       this.error = '';
  //     }, error => {
  //       console.log(error);
  //       this.error = error.message;
  //     }
  //   );
  // }

}
