import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {
  @Input() project: Project;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private location: Location) {}

  ngOnInit(): void {
    console.log('init');
    this.getProject();
  }

  getProject() {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getProjectById(id)
      .subscribe(project => this.project = project, error => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
