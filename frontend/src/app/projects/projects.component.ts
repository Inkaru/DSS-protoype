import { Component, OnInit } from '@angular/core';
import { ApiService } from "../api.service";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  projects;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getFakeProjects().subscribe((data) => {
      console.log(data);
      this.projects = data;
    });
  }

}
