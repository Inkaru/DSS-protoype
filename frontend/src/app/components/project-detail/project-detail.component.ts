import {Component, Input, OnInit} from '@angular/core';
import {Project} from '../../model/project';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';
import {AuthService} from '../../services/auth.service';
import {User} from '../../model/user';
import {ChatService} from '../../services/chat.service';

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

  colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService,
    private location: Location,
    private authService: AuthService,
    private chatService: ChatService) {}

  ngOnInit(): void {
    this.getProject();
  }

  getProject() {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getProjectById(id)
      .subscribe(project => {
        this.project = project;
        console.log(project);
        this.authService.currentUser.subscribe(x => {
          this.currentUser = x;
          this.followed = this.currentUser.followedProjects.map(p => p.id).includes(this.project.id);
          this.liked = this.currentUser.likedProjects.map(p => p.id).includes(this.project.id);
        });
      }, error => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }

  likeProject(id) {
    this.apiService.likeProject(id).subscribe(response => {
      this.authService.getCurrentUser();
      this.getProject();
    });
  }

  unlikeProject(id) {
    this.apiService.unlikeProject(id).subscribe(response => {
      this.authService.getCurrentUser();
      this.getProject();
    });
  }

  followProject(id) {
    this.apiService.followProject(id).subscribe(response => {
      this.authService.getCurrentUser();
      this.getProject();
    });
  }

  unfollowProject(id) {
    this.apiService.unfollowProject(id).subscribe(response => {
      this.authService.getCurrentUser();
      this.getProject();
    });
  }

  getRandomColor(text) {
    let hash = 0;
    for (let i = 0; i < text.length; i++) {
      hash = 31 * hash + text.charCodeAt(i);
    }
    const index = Math.abs(hash % this.colors.length);
    return this.colors[index];
  }

  createChat() {
    const arr = [];
    arr.push(this.currentUser.loginName);
    arr.push(this.project.creator.loginName);
    this.chatService.establishChannel({participantLoginNames: arr});
    this.router.navigate(['/chat']);
  }
}
