<template>
  <div id="app">
    <input placeholder="请输入密码" type="text" @keyup.enter="login" ref="input1">
    <button @click="friendAcquiring">交朋友</button>
    <div><span>{{result1}}</span></div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      result1:"111",
      username:"11111",
      path:"ws://localhost:8081/websocket/getFriends",
      websocket:""
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {
    friendAcquiring: function (){
      let a="getFriends"+this.$refs.input1.value
      this.socket.send(a)
        console.log(a);
    },


    init: function () {
      console.log("start");
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        this.socket.onmessage=this.message
        // 监听socket消息
      }
    },
    message:function (e){
      if(e.data==="没有数据")
       alert("没有数据")
      else{
        let dataJson = JSON.parse(e.data)
        for(var i in dataJson){

            console.log(dataJson[i].userId)
            console.log(dataJson[i].friendId)
            console.log(dataJson[i].lastTalkTime)

        }
      }
      },


    open: function () {
      console.log("socket连接成功")
    },
    error: function () {
      console.log("连接错误")
    },

    close: function () {
      console.log("socket已经关闭")
    },
    login() {
      this.result1=this.$refs.input1.value
    }

  },
  destroyed () {
    // 销毁监听
    this.socket.onclose = this.close
  }

};
</script>


