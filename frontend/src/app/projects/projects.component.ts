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
    this.apiService.getAllProjects().subscribe((data) => {
      console.log(data);
      this.projects = data;
    });
  }

  slideConfig = {
    "slidesToShow": 1,
    "slidesToScoll": 1,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": true,
    "autoplay": true
  };

}
