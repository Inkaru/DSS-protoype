import { Component, OnInit } from '@angular/core';
import {User} from '../../model/user';
import {AuthService} from '../../services/auth.service';
import {ChatService} from '../../services/chat.service';
import {ChatMessage} from '../../model/chatMessage';
import {ChatChannel} from '../../model/chatChannel';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  currentUser: User;
  messages: ChatMessage[] = [];
  currentChannel: ChatChannel;
  channels: ChatChannel[] = [];

  message = '';
  loginname = '';

  constructor(private authService: AuthService, private chatService: ChatService ) {
    this.authService.currentUser.subscribe(x => this.currentUser = x);
    this.chatService.messagesSubject.subscribe((data) => {
      this.messages = data;
      // scroll to bottom
    });
    this.chatService.channelsSubject.subscribe((data) => this.channels = data);
    this.chatService.channelSubject.subscribe(data => this.currentChannel = data);
  }

  ngOnInit(): void {
  }

  send(){
    this.chatService.sendChat(this.message);
  }

  establish(){
    const arr = [];
    arr.push(this.currentUser.loginName);
    arr.push(this.loginname);
    this.chatService.establishChannel({participantLoginNames: arr});
  }

  connectTo(id) {
    this.chatService.connectChat(id);
  }

  getMyChannels() {
    this.chatService.getMychannels();
    console.log(this.channels);
  }
}
