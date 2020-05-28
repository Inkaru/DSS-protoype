import {Component, Input, OnInit} from '@angular/core';
import {MarketplaceItem} from '../../model/marketplaceItem';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {Location} from '@angular/common';
import {ChatService} from '../../services/chat.service';

@Component({
  selector: 'app-marketplace-detail',
  templateUrl: './marketplace-detail.component.html',
  styleUrls: ['./marketplace-detail.component.css']
})
export class MarketplaceDetailComponent implements OnInit {
  @Input() item: MarketplaceItem;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private chatService: ChatService,
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

  createChat() {
    const arr = [];
    arr.push(this.item.creator.loginName);
    this.chatService.establishChannel({participantLoginNames: arr});
    this.router.navigate(['/chat']);
  }
}
