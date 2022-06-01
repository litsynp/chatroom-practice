<!DOCTYPE html>
<html lang="ko">

<head>
  <title>채팅방 - {{room.roomId}}</title>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
  <style>
    [v-cloak] {
      display: none;
    }
  </style>
</head>

<body>
<div class="container" id="app" v-cloak>
  <div>
    <h2>채팅방 "{{room.roomId}}"</h2>
  </div>
  <div class="input-group">
    <div class="input-group-prepend">
      <label class="input-group-text">내용</label>
    </div>
    <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
    <div class="input-group-append">
      <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
    </div>
  </div>
  <ul class="list-group">
    <li class="list-group-item" v-for="message in messages">
      <span class="text-muted">{{message.sender}}:</span> {{message.message}}
    </li>
  </ul>
  <div></div>
</div>

<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
  // websocket & stomp initialize
  let sock = new SockJS("/ws");
  let ws = Stomp.over(sock);
  let reconnect = 0;

  // Vue.js
  const vm = new Vue({
    el: "#app",
    data: {
      roomId: "",
      room: {},
      sender: "",
      message: "",
      messages: []
    },
    created() {
      this.roomId = localStorage.getItem("wschat.roomId");
      this.sender = localStorage.getItem("wschat.sender");
      this.findRoom();
    },
    methods: {
      findRoom: function () {
        axios.get("/api/v1/rooms/" + this.roomId)
        .then(response => {
          this.room = response.data;
        })
        .catch(e => {
          // Redirect to chat room list
          location.href = "/chat/rooms";
        });
      },
      sendMessage: function () {
        if (this.message.trim() !== "") {
          ws.send("/pub/comm/message", {}, JSON.stringify(
              {type: "COMM", roomId: this.roomId, sender: this.sender, message: this.message}));
          this.message = "";
        }
      },
      recvMessage: function (recv) {
        this.messages.unshift({
          "type": recv.type,
          "sender": recv.type === "ENTER" ? "[알림]" : recv.sender,
          "message": recv.message
        })
      }
    }
  });

  function connect() {
    // pub/sub event
    ws.connect({},
        frame => {
          ws.subscribe("/sub/comm/room/" + vm.$data.roomId, function (message) {
            const recv = JSON.parse(message.body);
            vm.recvMessage(recv);
          });
          ws.send("/pub/comm/message", {},
              JSON.stringify({
                type: "ENTER", roomId: vm.$data.roomId, sender: vm.$data.sender, message: ""
              }));
        },
        error => {
          if (reconnect++ <= 5) {
            setTimeout(function () {
              console.log("connection reconnect");
              sock = new SockJS("/ws");
              ws = Stomp.over(sock);
              connect();
            }, 10 * 1000);
          }
        });
  }

  connect();
</script>
</body>
</html>
