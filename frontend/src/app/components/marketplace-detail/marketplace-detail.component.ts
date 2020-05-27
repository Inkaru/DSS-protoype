import {Component, Input, OnInit} from '@angular/core';
import {MarketplaceItem} from '../../model/marketplaceItem';
import {ActivatedRoute} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-marketplace-detail',
  templateUrl: './marketplace-detail.component.html',
  styleUrls: ['./marketplace-detail.component.css']
})
export class MarketplaceDetailComponent implements OnInit {
  @Input() item: MarketplaceItem;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private location: Location) {
  }

  ngOnInit(): void {
    this.getItem();
  }

  getItem() {
    const id = this.route.snapshot.paramMap.get('id');
    this.apiService.getMPItemById(id)
      .subscribe(item => {
        this.item = item;
        console.log(item);
      }, error => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
