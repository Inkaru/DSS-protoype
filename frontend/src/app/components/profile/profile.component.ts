import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {User} from '../../model/user';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {

  constructor(
    private authService: AuthService,
    private apiService: ApiService,
    private formBuilder: FormBuilder) {
    this.authService.currentUser.subscribe(
      x => {
        this.currentUser = x;
      });
  }

  currentUser: User = null;
  projects: Project[];
  projectsForm: FormGroup;
  projectsSubscription: Subscription;

  indexToUpdate;
  editMode = false;

  profileForm: FormGroup;
  error: '';

  slideConfig = {
    slidesToShow: 1,
    slidesToScoll: 1,
    nextArrow: '<div class=\'nav-btn next-slide\'></div>',
    prevArrow: '<div class=\'nav-btn prev-slide\'></div>',
    dots: true,
    autoplay: true
  };


  ngOnInit(): void {
    this.initProjectsForm();
    this.projectsSubscription = this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
  }


  updateProfile() {
    this.apiService.updateUser(this.currentUser).subscribe(response => {
      this.authService.getCurrentUser();
      // @ts-ignore
      $('#profileFormModal').modal('hide');
    }, error => {
      console.log(error);
      this.error = error.message;
    });
  }

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
      this.apiService.createProject(newProject);
    }
  }

  ngOnDestroy() {
    this.projectsSubscription.unsubscribe();
  }

  resetForm() {
    this.editMode = false;
    this.projectsForm.reset();
  }

  onDeleteProject(id) {
    if (confirm('Are you sure you want delete this project?')) {
      this.apiService.deleteProjectById(id).subscribe(value => {
        console.log('project deleted successfully');
        // todo should work
        this.authService.getCurrentUser();
      }, error1 => console.log(error1));
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
