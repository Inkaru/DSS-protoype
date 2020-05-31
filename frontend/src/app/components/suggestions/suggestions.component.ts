import { Component, OnInit } from '@angular/core';
import {Project} from '../../model/project';
import {User} from '../../model/user';
import {ApiService} from '../../services/api.service';

@Component({
  selector: 'app-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.css']
})
export class SuggestionsComponent implements OnInit {

  projectReco: Project[] = [];
  userReco: User[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getProjectRecommandation().subscribe(response => {
      this.projectReco = response;
    }, error => {
      console.log(error);
    });
    this.apiService.getUserRecommandation().subscribe(response => {
      this.userReco = response;
      console.log(this.userReco);
    }, error => {
      console.log(error);
    });
  }

}
