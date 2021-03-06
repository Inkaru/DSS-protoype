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

  displayName: string;
  firstName: string;
  lastName: string;

  email: string;
  phoneNumber: string;

  description: string;

  location: Loc;

  fieldOfActivityTags: string[];

  followedProjects: Project[];
  likedProjects: Project[];
  createdProjects: Project[];
  marketplaceItems: MarketplaceItem[];
}
