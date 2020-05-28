import {User} from './user';
import {Hashtag} from './hashtag';

export class Project {
  id: string;
  name: string;
  description: string;
  creator: User;
  participants: User[];
  follows: User[];
  likes: User[];
  hashtags: Hashtag[];
}
