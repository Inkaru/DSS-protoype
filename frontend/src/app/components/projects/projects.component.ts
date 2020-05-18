import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';

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

  error: string;

  constructor(
    private apiService: ApiService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.initProjectsForm();
    this.projectsSubscription = this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
  }

  slideConfig = {
    'slidesToShow': 1,
    'slidesToScoll': 1,
    'nextArrow': '<div class=\'nav-btn next-slide\'></div>',
    'prevArrow': '<div class=\'nav-btn prev-slide\'></div>',
    'dots': true,
    'autoplay': true
  };

  initProjectsForm() {
    this.projectsForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmitProjectsForm() {
    const newProject = this.projectsForm.value;
    if (this.editMode) {
      this.apiService.updateProjects(newProject, this.indexToUpdate);
    } else {
      this.apiService.createProject(newProject).subscribe(data => {
          console.log('success');
          console.log(data);
          this.apiService.getAllProjects();
          // @ts-ignore
          $('#projectsFormModal').modal('hide');
        },
        error => {
          console.log(error);
          this.error = error.error.message;
        });
    }
  }

  ngOnDestroy() {
    this.projectsSubscription.unsubscribe();
  }

  resetForm() {
    this.editMode = false;
    this.projectsForm.reset();
  }

  onDeleteProject(index) {
    if (confirm('Are you sure you want delete this project?')) {
      this.apiService.deleteProjects(index);
    }
  }

  onEditProject(project) {
    this.editMode = true;
    // @ts-ignore
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
