<template>
  <div id="app">

  </div>
</template>

<script>
export default {
  data () {
    return {
      path:"ws://localhost:8081/websocket/getFriends",
      socket:""
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {
    init: function () {
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error

        // 监听socket消息
      }
    },
    friendAcquiring:function (){
      let a="11111"
      this.socket.send(a)
    },
    open: function () {
      console.log("socket连接成功")
    },
    error: function () {
      console.log("连接错误")
    },
    getMessage: function (msg) {
      console.log(msg.data)
    },
    send: function () {
      let a="11111"
      console.log(a);
      if(this.socket.readyState==this.socket.open){
        alert(a)
        this.socket.send(a)
      }

    },
    close: function () {
      console.log("socket已经关闭")
    }
  },
  destroyed () {
    // 销毁监听
    this.socket.onclose = this.close
  }
}
</script>

<style>

</style>
