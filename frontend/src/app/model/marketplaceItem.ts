import {User} from './user';

export class MarketplaceItem {
  id: string;
  name: string;
  price: number;
  description: string;
  city: string;
  country: string;
  type: string;
  creator: User;
}
