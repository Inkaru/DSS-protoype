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
        console.log(data);
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
    this.apiService.emitProjects();
  }

  onSubmitProjectsForm() {
    if (this.projectsForm.invalid){
      this.error = 'All fields must be filled';
      return;
    }
    this.apiService.createProject(this.projectsForm.value).subscribe(
      response => {
        console.log(response);
        this.apiService.getAllProjects();
        // @ts-ignore
        $('#projectsFormModal').modal('hide');
        this.error = '';
      }, error => {
        console.log(error);
        this.error = error.message;
      }
    );
  }
}
