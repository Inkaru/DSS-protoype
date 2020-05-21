import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {
  @Input() project: Project;

  liked = false;
  followed = false;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private location: Location,
    private authService: AuthService) {}

  ngOnInit(): void {
    console.log('init');
    this.getProject();
    this.liked = this.authService.currentUserValue.likedProjects.includes(this.project);
    this.followed = this.authService.currentUserValue.followedProjects.includes(this.project);
  }

  getProject() {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getProjectById(id)
      .subscribe(project => this.project = project, error => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }

  likeProject(id) {
    this.apiService.likeProject(id).subscribe(response => {
      this.liked = true;
      this.authService.currentUserValue.likedProjects.push(this.project);
    });
  }

  unlikeProject(id) {
    this.apiService.unlikeProject(id).subscribe(response => {
      this.liked = false;
      const index = this.authService.currentUserValue.likedProjects.indexOf(this.project);
      this.authService.currentUserValue.likedProjects.slice(index, 1);
    });
  }

  followProject(id) {
    this.apiService.followProject(id).subscribe(response => {
      this.followed = true;
      this.authService.currentUserValue.followedProjects.push(this.project);
    });
  }

  unfollowProject(id) {
    this.apiService.unfollowProject(id).subscribe(response => {
      this.followed = false;
      const index = this.authService.currentUserValue.followedProjects.indexOf(this.project);
      this.authService.currentUserValue.followedProjects.slice(index, 1);
    });
  }
}
