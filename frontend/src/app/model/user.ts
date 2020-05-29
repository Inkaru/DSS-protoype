import {Project} from './project';
import {MarketplaceItem} from './marketplaceItem';
import {Loc} from './location';

export class User {
  constructor() {
    this.location = new Loc();
  }

  id: string;
  loginName: string;
  type: string;
  title: string;

  firstName: string;
  lastName: string;

  email: string;
  phoneNumber: string;

  description: string;

  location: Loc;

  followedProjects: Project[];
  likedProjects: Project[];
  createdProjects: Project[];
  marketplaceItems: MarketplaceItem[];
}
