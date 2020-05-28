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

  getRandomColor() {
    const color = Math.floor(0x1000000 * Math.random()).toString(16);
    return '#' + ('000000' + color).slice(-6);
  }

  createChat() {
    const arr = [];
    arr.push(this.currentUser.loginName);
    arr.push(this.project.creator.loginName);
    this.chatService.establishChannel({participantLoginNames: arr});
    this.router.navigate(['/chat']);
  }
}
