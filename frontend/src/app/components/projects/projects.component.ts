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

  projects: Project[];

  constructor(
    private apiService: ApiService
  ) { }

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

  slideConfig = {
    'slidesToShow': 1,
    'slidesToScoll': 1,
    'nextArrow': '<div class=\'nav-btn next-slide\'></div>',
    'prevArrow': '<div class=\'nav-btn prev-slide\'></div>',
    'dots': true,
    'autoplay': true
  };

}
