import request from '../utils/request';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export function onLoginServer(userName) {
  console.log('post a request');
  // 连接 Websocket 服务端
  let myHeaders = new Headers({
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'text/plain'
  });
  var options = { mode: 'cors' , headers: myHeaders};
  var socket = new SockJS("http://localhost:8080/pocker-websocket", options);
  var stompClient = Stomp.over(socket);
  stompClient.connect({ login: true }, function (frame) {

    console.log('Connected: ' + frame);
    stompClient.subscribe('/ricky/topic/greetings', function (greeting) {

    });
  });
}

