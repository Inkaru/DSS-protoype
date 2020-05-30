export class Resource {
  constructor(name, description, img, link) {
    this.name = name;
    this.description = description;
    this.img = img;
    this.link = link;
  }

  name: string;
  description: string;
  img: string;
  link: string;
}
