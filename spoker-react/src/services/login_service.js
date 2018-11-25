import request from '../utils/request';
import SockJS from 'sockjs-client';

export function onLoginServer(userName) {
  console.log('post a request');
  var socket = new SockJS('/pocker-websocket');
    console.log('xxxx');
  var stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/app/login', function (greeting) {
            
        });
    });
  return request('http://localhost:8080/app/login/'+userName);
}

