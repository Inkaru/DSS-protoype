import {User} from './user';
import {Loc} from './location';

export class Project {

  constructor() {
    this.location = new Loc();
    this.hashTags = [];
  }

  id: string;
  name: string;
  description: string;
  creator: User;
  participants: User[];
  follows: User[];
  likes: User[];
  hashTags: string[];
  location: Loc;
}
