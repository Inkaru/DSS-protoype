import {Project} from './project';
import {MarketplaceItem} from './marketplaceItem';

export class User {
  id: string;
  loginName: string;
  description: string;
  email: string;
  phoneNumber: string;
  city: string;
  country: string;
  followedProjects: Project[];
  likedProjects: Project[];
  createdProjects: Project[];
  participatedProjects: Project[];
  marketplaceItems: MarketplaceItem[];
  address: string;
  firstName: string;
  lastName: string;
  type: string;
  title: string;
}
