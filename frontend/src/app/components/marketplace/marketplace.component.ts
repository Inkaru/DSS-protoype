import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../services/api.service';
import {MarketplaceItem} from '../../model/marketplaceItem';

@Component({
  selector: 'app-marketplace',
  templateUrl: './marketplace.component.html',
  styleUrls: ['./marketplace.component.css']
})
export class MarketplaceComponent implements OnInit {

  constructor(private apiService: ApiService) {
    this.currentItem = new MarketplaceItem();
  }

  items: MarketplaceItem[] = [];
  currentItem: MarketplaceItem;

  type = 'ALL';

  error = '';

  ngOnInit() {
    this.apiService.mpItemsSubject.subscribe(
      (x) => {
        this.items = x;
        console.log(x);
      }
    );
    this.filterItems();
  }

  onSubmitItemForm() {
    this.apiService.createMPItem(this.currentItem).subscribe(
      response => {
        console.log('created item');
        this.currentItem = new MarketplaceItem();
        this.filterItems();
        // @ts-ignore
        $('#itemFormModal').modal('hide');
      }, error => {
        console.log(error);
        this.error = error.message;
      }
    );
  }

  filterItems() {
    switch (this.type) {
      case 'ALL': {
        this.apiService.getAllMPItems();
        break;
      }
      case 'OFFER': {
        this.apiService.getOffersMPItems();
        break;
      }
      case 'REQUEST': {
        this.apiService.getRequestsMPItems();
        break;
      }
    }
  }
}
