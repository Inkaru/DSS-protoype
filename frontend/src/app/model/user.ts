import {Project} from "./project";

export class User {
  id: string;
  loginName: string;
  hashedPassword: string;
  firstName: string;
  lastName: string;
  title: string;
  city: string;
  country: string;
  description: string;
  emailAddress: string;
  phoneNumber: string;
  address: string;
  followedProjects: Project[];
  likedProjects: Project[];
}
