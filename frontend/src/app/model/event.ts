export class CalenderEvent {
  constructor(name, date, description, img, link) {
    this.name = name;
    this.date = date;
    this.description = description;
    this.img = img;
    this.link = link;
  }

  name: string;
  date: string;
  description: string;
  img: string;
  link: string;
}
