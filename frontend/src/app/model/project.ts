import {User} from './user';

export class Project {

  name: string;
  description: string;

  // TO DO
  id: string;
  creators: Array<Project>;
  participants: Array<Project>;

}
