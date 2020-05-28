import {Project} from './project';
import {MarketplaceItem} from './marketplaceItem';

export class User {
  id: string;
  loginName: string;
  type: string;
  title: string;

  firstName: string;
  lastName: string;

  email: string;
  phoneNumber: string;

  description: string;

  location: Location;

  followedProjects: Project[];
  likedProjects: Project[];
  createdProjects: Project[];
  marketplaceItems: MarketplaceItem[];
}
