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
    this.chatService.getMychannels();
  }

  ngOnInit(): void {
    this.connectTo(null);
  }

  send(){
    this.chatService.sendChat(this.message);
    this.message = '';
  }

  establish(){
    const arr = [];
    arr.push(this.loginname);
    this.chatService.establishChannel({participantLoginNames: arr});
    this.loginname = '';
  }

  connectTo(channel) {
    if (channel){
      this.chatService.connectChat(channel.id);
      this.currentChannel = channel;
      this.chatService.updateMessages(channel);
    } else {
      this.chatService.connectChat(null);
      this.currentChannel = null;
      this.chatService.updateMessages(null);
    }
  }
}
