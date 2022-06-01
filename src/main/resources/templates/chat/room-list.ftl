<!DOCTYPE html>
<html lang="ko">

<head>
  <title>채팅방 목록</title>
  <meta charset="utf-8">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <!-- CSS -->
  <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
  <style>
    [v-cloak] {
      display: none;
    }
  </style>
</head>

<body>
<div class="container" id="app" v-cloak>
  <div class="row">
    <div class="col-md-12">
      <h3>채팅방 리스트</h3>
    </div>
  </div>
  <div class="input-group">
    <div class="input-group-prepend">
      <label class="input-group-text">방 제목</label>
    </div>
    <input type="text" class="form-control" v-model="roomName" @keyup.enter="createRoom">
    <div class="input-group-append">
      <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
    </div>
  </div>
  <ul class="list-group">
    <li class="list-group-item list-group-item-action"
        v-for="item in chatRooms"
        v-bind:key="item.roomId"
        v-on:click="enterRoom(item.roomId)">
      {{item.roomId}}
    </li>
  </ul>
</div>

<!-- JavaScript -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script>
  // Delete previous usage logs
  localStorage.clear();

  const vm = new Vue({
    el: "#app",
    data: {
      roomName: "",
      chatRooms: []
    },
    created() {
      this.findAllRooms();
    },
    methods: {
      findAllRooms: function () {
        axios.get("/api/v1/rooms").then(response => {
          this.chatRooms = response.data;
        });
      },
      createRoom: function () {
        if (!this.roomName || "" === this.roomName.trim()) {
          alert("방 제목을 입력하세요.");
          return;
        }

        const data = {
          "name": this.roomName
        };

        axios.post("/api/v1/rooms", data)
        .then(response => {
          alert("\"" + response.data.roomId + "\" 방 개설에 성공했습니다.");
          this.roomName = "";
          this.findAllRooms();
        })
        .catch(e => {
          console.log(e.response.data)
          alert("채팅방 개설에 실패했습니다.");
        });
      },
      enterRoom: function (roomId) {
        const sender = prompt("대화명을 입력해 주세요.");

        if (!sender || "" === sender.trim()) {
          alert("올바른 대화명을 입력해야 채팅방에 입장할 수 있습니다.")
          return;
        }

        localStorage.setItem("wschat.sender", sender);
        localStorage.setItem("wschat.roomId", roomId);
        location.href = "/chat/rooms/" + roomId;
      }
    }
  });
</script>
</body>
</html>
