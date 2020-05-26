import {User} from './user';

export class Project {
  id: string;
  name: string;
  description: string;
  creator: User;
  participants: User[];
  follows: User[];
  likes: User[];
}
