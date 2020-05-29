import {Component, OnInit} from '@angular/core';
import {CalenderEvent} from '../../model/event';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  events: CalenderEvent[];

  constructor() {
    this.events = [];
    this.events.push(new CalenderEvent('EU Green Week 2020',
      'June 1 - June 5',
      'EU Green Week is the biggest European annual event dedicated to environment policy. The main focus of the event will be put on nature and biodiversity.',
      'https://movecreative.eu/wp-content/uploads/2019/08/загруженное-2.png',
      'https://movecreative.eu/event/eu-green-week-2020/'));
    this.events.push(new CalenderEvent('Gdynia Design Days 2020',
      'June 4 - June 12',
      'World Design Weeks is a network for design weeks and festivals around the globe. Gdynia Design Days offers a platform for inspiration as well as practical and valuable design knowledge. Within a frame of the global design fair you will be able to witness the outputs of the CTCC project. The prototypes of products, services or business- processes for traditional SMEs will be presented for all the guests of the international event.',
      'https://movecreative.eu/wp-content/uploads/2019/08/wdg_event_image-300x167.jpg',
      'https://movecreative.eu/event/gdynia-design-days-2020/'));
    this.events.push(new CalenderEvent('ACE Orlando',
      'June 14 - June 17',
      'World Design Weeks is a network for design weeks and festivals around the globe. Gdynia Design Days offers a platform for inspiration as well as practical and valuable design knowledge. Within a frame of the global design fair you will be able to witness the outputs of the CTCC project. The prototypes of products, services or business- processes for traditional SMEs will be presented for all the guests of the international event.',
      'https://movecreative.eu/wp-content/uploads/2019/08/main.jpg',
      'https://movecreative.eu/event/ace-orlando/'));
  }

  ngOnInit(): void {
  }

}
