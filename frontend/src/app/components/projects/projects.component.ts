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


  

  error: string;

  projects = [];
  projectsSubscription: Subscription;

  constructor(
    private apiService: ApiService
  ) { }

  ngOnInit() {
    this.projectsSubscription = this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
      }
    );
    this.apiService.getAllProjects();
    this.apiService.emitProjects();
  }



  ngOnDestroy() {
    this.projectsSubscription.unsubscribe();
  }

  slideConfig = {
    'slidesToShow': 1,
    'slidesToScoll': 1,
    'nextArrow': '<div class=\'nav-btn next-slide\'></div>',
    'prevArrow': '<div class=\'nav-btn prev-slide\'></div>',
    'dots': true,
    'autoplay': true
  };

  

}
