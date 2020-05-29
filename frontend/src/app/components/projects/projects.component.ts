import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {Project} from '../../model/project';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {concat} from 'rxjs';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  constructor(
    private apiService: ApiService,
    private formBuilder: FormBuilder
  ) {
    this.projectsForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  error: string;

  currentProject: Project = new Project();
  tag = '';

  selectedTag = '';
  tags: string[] = [];
  selectedCities = '';
  cities: string[] = [];

  projects: Project[];
  filteredProjects: Project[];

  projectsForm: FormGroup;

  slideConfig = {
    slidesToShow: 1,
    slidesToScoll: 1,
    nextArrow: '<div class=\'nav-btn next-slide\'></div>',
    prevArrow: '<div class=\'nav-btn prev-slide\'></div>',
    dots: true,
    autoplay: true
  };

  ngOnInit() {
    this.apiService.projectsSubject.subscribe(
      (data: any) => {
        this.projects = data;
        this.filterProjects();
        this.updateTags();
      }
    );
    this.apiService.getAllProjects();
  }

  addTag() {
    if (this.tag !== '') {
      this.currentProject.tags.push('#' + this.tag);
      this.tag = '';
    }
  }

  removeTag(tag: string) {
    if (tag !== '') {
      const index = this.currentProject.tags.indexOf(tag);
      console.log(tag);
      console.log(index);
      if (index !== -1) {
        this.currentProject.tags.splice(index, 1);
      }
    }
  }

  onSubmitProjectsForm() {
    this.apiService.createProject(this.currentProject).subscribe(data => {
        console.log('success');
        console.log(data);
        this.apiService.getAllProjects();
        this.currentProject = new Project();
        // @ts-ignore
        $('#projectFormModal').modal('hide');
      },
      error => {
        console.log(error);
        this.error = error.error.message;
      });
  }

  filterProjects() {
    let tmp = this.projects;
    if (this.selectedTag !== ''){
      console.log('filter with tag' + this.selectedTag);
      tmp = tmp.filter(p => p.tags.includes(this.selectedTag));
    }

    if (this.selectedCities !== ''){
      console.log('filter with city' + this.selectedCities);
      tmp = tmp.filter(p => p.location.city === this.selectedCities);
    }

    this.filteredProjects = tmp;
  }

  updateTags() {
    const tmp = new Set<string>();
    for (const p of this.projects) {
      for (const tag of p.tags) {
        tmp.add(tag);
      }
    }
    this.tags = Array.from(tmp);

    this.cities = Array.from(this.projects.map(p => p.location.city));
  }

  // onSubmitProjectsForm() {
  //   if (this.projectsForm.invalid){
  //     this.error = 'All fields must be filled';
  //     return;
  //   }
  //   this.apiService.createProject(this.projectsForm.value).subscribe(
  //     response => {
  //       console.log(response);
  //       this.apiService.getAllProjects();
  //       // @ts-ignore
  //       $('#projectsFormModal').modal('hide');
  //       this.error = '';
  //     }, error => {
  //       console.log(error);
  //       this.error = error.message;
  //     }
  //   );
  // }

}
