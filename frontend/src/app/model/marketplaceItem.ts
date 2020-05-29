import {User} from './user';
import {Loc} from './location';

export class MarketplaceItem {

  constructor() {
    this.location = new Loc();
  }

  id: string;
  name: string;
  price: number;
  description: string;
  location: Loc;
  type: string;
  creator: User;
}
