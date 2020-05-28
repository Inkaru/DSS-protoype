import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {ChatMessage} from '../model/chatMessage';
import {Observable, Observer, Subject} from 'rxjs';
import {ChatChannel} from '../model/chatChannel';

// declare var SockJS;
// declare var Stomp;

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  channelId = null;
  stompClient = null;

  messages: ChatMessage[] = [];
  messagesSubject = new Subject<ChatMessage[]>();

  channel: ChatChannel;
  channelSubject = new Subject<ChatChannel>();

  channels: ChatChannel[] = [];
  channelsSubject = new Subject<ChatChannel[]>();

  url = '/ws';

  constructor(private httpClient: HttpClient) {
  }

  getMychannels(){
    this.httpClient.get('/api/chat/getMyChannels').subscribe((response: ChatChannel[]) => {
      this.channels = response;
      this.channelsSubject.next(this.channels);
    });
  }

  connectChat(id){
    // choose channel id
    this.channelId = id;
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => this.onConnected(), () => this.onError());
    event.preventDefault();
  }

  establishChannel(participants){
    const header = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    const data = JSON.stringify(participants);
    this.httpClient.post('/api/chat/establishChannel', data, {headers: header} ).subscribe((response: ChatChannel) => {
      this.channel = response;
      this.channelSubject.next(this.channel);
      this.messages = this.channel.messages;
      this.messagesSubject.next(this.messages);
      this.getMychannels();
    });
  }

  sendChat(message){
    const content = message.trim();
    console.log('message : ' + content);
    console.log(this.stompClient);
    if (content){
      console.log('sending to :');
      if (this.channelId){
        console.log(this.channelId);
        this.stompClient.send('/api/chat/' + this.channelId, {}, JSON.stringify(content));
      } else {
        console.log('public chat');
        this.stompClient.send('/api/chat/publicChat', {}, JSON.stringify(content));
      }
    }
  }

  onConnected(){
    if (this.channelId) {
      this.stompClient.subscribe('/topic/chat.' + this.channelId, this.onMessageReceived);
    } else {
      this.stompClient.subscribe('/topic/publicChat', this.onMessageReceived);
    }
    event.preventDefault();
  }

  onError(){
    console.log('error');
  }

  onMessageReceived(payload){
    const message = JSON.parse(payload.body);
    console.log(message);
    this.messages.push(message);
    this.messagesSubject.next(this.messages);
  }
}
