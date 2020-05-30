import {Component, OnInit, Renderer2} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private renderer: Renderer2) {
    this.renderer.setStyle(document.body, 'background', 'linear-gradient(rgba(0, 0, 150, 0.5), rgba(0, 0, 50, 0.1)),url(\'../../../assets/bg-title.jpeg\') no-repeat center');
    this.renderer.setStyle(document.body, 'background-size', '100%');
  }

  ngOnInit(): void {
  }

}
