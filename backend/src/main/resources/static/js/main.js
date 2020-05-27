'use strict';

var channelPage = document.querySelector('#channel-page');
var chatPage = document.querySelector('#chat-page');
var channelForm = document.querySelector('#channelForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var channelId = null;
var stompClient = null;

var colors = [
  '#2196F3', '#32c787', '#00BCD4', '#ff5652',
  '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connectChat(event) {
  channelId = document.querySelector('#channelId').value.trim();
  channelPage.classList.add('hidden');
  chatPage.classList.remove('hidden');
  var socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnected, onError);
  event.preventDefault();
}


function onConnected() {
  if (channelId) {
    stompClient.subscribe('/topic/chat.' + channelId, onMessageReceived);
  } else {
    stompClient.subscribe('/topic/publicChat', onMessageReceived);
  }
  connectingElement.classList.add('hidden');
}


function onError(error) {
  connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
  connectingElement.style.color = 'red';
}


function send(event) {
  var messageContent = messageInput.value.trim();

  if (messageContent && stompClient) {
    var chatMessage = {
      content: messageInput.value
    };
    if (channelId) {
      stompClient.send("/app/chat/" + channelId, {}, JSON.stringify(chatMessage));
    } else {
      stompClient.send("/app/chat/publicChat", {}, JSON.stringify(chatMessage));
    }
    messageInput.value = '';
  }
  event.preventDefault();
}


function onMessageReceived(payload) {
  var message = JSON.parse(payload.body);

  var messageElement = document.createElement('li');


  messageElement.classList.add('chat-message');

  var avatarElement = document.createElement('i');
  var firstLetter = document.createTextNode(message.authorLoginName[0]);
  avatarElement.appendChild(firstLetter);
  avatarElement.style['background-color'] = getAvatarColor(message.authorLoginName);

  messageElement.appendChild(avatarElement);

  var usernameElement = document.createElement('span');
  var usernameText = document.createTextNode(message.authorLoginName + " (" + message.timeSent + ")");
  usernameElement.appendChild(usernameText);
  messageElement.appendChild(usernameElement);


  var textElement = document.createElement('p');
  var messageText = document.createTextNode(message.content);
  textElement.appendChild(messageText);

  messageElement.appendChild(textElement);

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
  var hash = 0;
  for (var i = 0; i < messageSender.length; i++) {
    hash = 31 * hash + messageSender.charCodeAt(i);
  }
  var index = Math.abs(hash % colors.length);
  return colors[index];
}
channelForm.addEventListener('submit', connectChat, true)
messageForm.addEventListener('submit', send, true)
