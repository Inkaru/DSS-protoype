import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {User} from '../../model/user';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MarketplaceItem} from '../../model/marketplaceItem';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(
    private authService: AuthService,
    private apiService: ApiService,
    private formBuilder: FormBuilder) {
    this.authService.currentUser.subscribe(
      x => {
        this.currentUser = x;
      });
    this.currentProject = new Project();
    this.currentItem = new MarketplaceItem();
  }

  currentUser: User = null;

  currentProject: Project;
  currentItem: MarketplaceItem;
  editMode = false;

  error: '';
  tag = '';

  colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
  ];

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
    this.currentItem = new MarketplaceItem();
    this.currentProject = new Project();
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

  addTag(){
    if (this.tag !== '' ){
      this.currentProject.tags.push('#' + this.tag);
      this.tag = '';
    }
  }

  removeTag(tag: string) {
    if (tag !== '' ){
      const index = this.currentProject.tags.indexOf(tag);
      console.log(tag);
      console.log(index);
      if (index !== -1){
        this.currentProject.tags.splice(index, 1);
      }
    }
  }

  addFieldTag(){
    if (this.tag !== '' ){
      this.currentUser.fieldOfActivityTags.push('#' + this.tag);
      this.tag = '';
    }
  }

  removeFieldTag(tag: string) {
    if (tag !== '' ){
      const index = this.currentUser.fieldOfActivityTags.indexOf(tag);
      console.log(tag);
      console.log(index);
      if (index !== -1){
        this.currentUser.fieldOfActivityTags.splice(index, 1);
      }
    }
  }

  getRandomColor(text) {
    // const color = Math.floor(0x1000000 * Math.random()).toString(16);
    // return '#' + ('000000' + color).slice(-6);
    let hash = 0;
    for (let i = 0; i < text.length; i++) {
      hash = 31 * hash + text.charCodeAt(i);
    }
    const index = Math.abs(hash % this.colors.length);
    return (this.colors)[index];
  }

  onSubmitItemForm() {
    if (this.editMode) {
      this.apiService.updateMPItem(this.currentItem).subscribe(
        response => {
          console.log('item updated');
          this.authService.getCurrentUser();
          this.currentItem = new MarketplaceItem();
          // @ts-ignore
          $('#itemFormModal').modal('hide');
        }, error => {
          console.log(error);
        }
      );
    } else {
      this.apiService.createMPItem(this.currentItem).subscribe(data => {
          console.log('success');
          console.log(data);
          this.authService.getCurrentUser();
          this.currentItem = new MarketplaceItem();
          // @ts-ignore
          $('#itemFormModal').modal('hide');
        },
        error => {
          console.log(error);
          this.error = error.error.message;
        });
    }
  }

  onDeleteItem(id: string) {
    if (confirm('Are you sure you want delete this item?')) {
      this.apiService.deleteMPItemById(id).subscribe(value => {
        console.log('item deleted successfully');
        this.authService.getCurrentUser();
      }, error => console.log(error));
    }
  }

  onEditItem(item: MarketplaceItem) {
    this.editMode = true;
    this.currentItem = item;
    // @ts-ignore
    $('#itemFormModal').modal('show');
  }
}
