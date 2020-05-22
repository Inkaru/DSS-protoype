import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';
import {AuthService} from '../../services/auth.service';
import {User} from '../../model/user';

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.css']
})
export class ProjectDetailComponent implements OnInit {
  @Input() project: Project;

  liked = false;
  followed = false;

  currentUser: User;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private location: Location,
    private authService: AuthService) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getProjectById(id)
      .subscribe(project => {
        this.project = project;
        this.authService.currentUser.subscribe(x => {
          this.currentUser = x;
          this.followed = this.currentUser.followedProjects.map(p => p.id).includes(this.project.id);
          this.liked = this.currentUser.likedProjects.map(p => p.id).includes(this.project.id);
        });
      }, error => this.goBack());

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
      this.authService.getCurrentUser();
    });
  }

  unlikeProject(id) {
    this.apiService.unlikeProject(id).subscribe(response => {
      this.authService.getCurrentUser();
    });
  }

  followProject(id) {
    this.apiService.followProject(id).subscribe(response => {
      this.authService.getCurrentUser();
    });
  }

  unfollowProject(id) {
    this.apiService.unfollowProject(id).subscribe(response => {
      this.authService.getCurrentUser();
    });
  }
}
