import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {User} from '../../model/user';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private apiService: ApiService,
    private formBuilder: FormBuilder) {
    this.authService.currentUser.subscribe(
      x => {
        this.currentUser = x;
        this.projects = x.createdProjects;
      });
    this.currentProject = new Project();
  }

  currentUser: User = null;
  projects: Project[];

  projectsForm: FormGroup;

  indexToUpdate;
  currentProject: Project;
  editMode = false;

  error: '';

  ngOnInit(): void {
    this.initProjectsForm();
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
    if (this.editMode) {
      this.apiService.updateProject(this.currentProject).subscribe(
        response => {
          console.log('project updated');
          this.authService.getCurrentUser();
          this.currentProject = new Project();
          // @ts-ignore
          $('#projectFormModal').modal('hide');
        }, error => {
          console.log(error);
        }
      );
    } else {
      this.apiService.createProject(this.currentProject).subscribe(data => {
          console.log('success');
          console.log(data);
          this.authService.getCurrentUser();
          this.currentProject = new Project();
          // @ts-ignore
          $('#projectFormModal').modal('hide');
        },
        error => {
          console.log(error);
          this.error = error.error.message;
        });
    }
  }

  resetForm() {
    this.editMode = false;
    this.projectsForm.reset();
  }

  onDeleteProject(id) {
    if (confirm('Are you sure you want delete this project?')) {
      this.apiService.deleteProjectById(id).subscribe(value => {
        console.log('project deleted successfully');
        this.authService.getCurrentUser();
      }, error1 => console.log(error1));
    }
  }

  onEditProject(project) {
    this.editMode = true;
    this.currentProject = project;
    // @ts-ignore
    $('#projectFormModal').modal('show');

  }

  getRandomColor() {
    const color = Math.floor(0x1000000 * Math.random()).toString(16);
    return '#' + ('000000' + color).slice(-6);
  }
}
