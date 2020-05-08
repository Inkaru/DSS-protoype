import {User} from "./user";

export class Project {
  id: string;
  name: string;
  description: string;
  creators: User[];
  participants: User[];

}
