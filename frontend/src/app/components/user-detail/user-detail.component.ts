import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {AuthService} from '../../services/auth.service';
import {ChatService} from '../../services/chat.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  @Input() user: User;

  currentUser: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private apiService: ApiService,
    private authService: AuthService,
    private chatService: ChatService
  ) { }

  ngOnInit(): void {
  }

  getUser() {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getUserById(id)
      .subscribe(user => {
        this.user = user;
        console.log(user);
      }, error => this.goBack());
    this.authService.currentUser.subscribe(x => {
      this.currentUser = x;
    });
  }

  goBack(): void {
    this.location.back();
  }

  createChat() {
    const arr = [];
    arr.push(this.user.loginName);
    this.chatService.establishChannel({participantLoginNames: arr});
    this.router.navigate(['/chat']);
  }

}
