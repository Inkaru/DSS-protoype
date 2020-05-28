import {ChatMessage} from './chatMessage';

export class ChatChannel {
  id: string;
  participantLoginNames: string[];
  messages: ChatMessage[];
}
