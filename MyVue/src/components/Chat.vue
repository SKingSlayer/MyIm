<template>
  <div id="app">
    <input placeholder="请输入密码" type="text" @keyup.enter="login" ref="input1">
    <input placeholder="请输入密码" type="text" @keyup.enter="login" ref="input2">
    <input placeholder="请输入信息" type="text" @keyup.enter="login" ref="input3">
    <button @click="friendAcquiring">获取朋友</button>
    <button @click="addFriend">添加朋友</button>
    <button @click="sendMessage">发送消息</button>
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
    addFriend:function (){
    let a="addFriend"+JSON.stringify({
      userId:1,
      friendId:this.$refs.input2.value,
      lastTalkTime:this.dateFormat(new Date())
    })
    this.socket.send(a);
    },

    sendMessage:function (){
      let a="sendMessage"+JSON.stringify({
          userId:1,
          friendId:2,
          message:this.$refs.input3.value,
          talkTime:this.dateFormat(new Date())
        }
      )
      this.socket.send(a);
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
        this.timer=setTimeout(this.initConnect,200)
        console.log(1111)
      }
    },
    initConnect:function(){
      let a="hello"+JSON.stringify(
        {
          userId:1,
          friendId:2,
          message:this.$refs.input3.value,
          talkTime:this.dateFormat(new Date())
        })
      console.log(a)
      this.socket.send(a)
    },
    dateFormat(time) {
      var date=new Date(time);
      var year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
      var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
      var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    message:function (e){
      if(e.data=="已连接"){
        alert(e.data)
      }
      else
        alert(e.data)
      if(e.data.indexOf("ack")===0){
        let a="ack"+JSON.stringify({
          userId:1,
          friendId:2,
          lastTalkTime:this.dateFormat(new Date()),

        })
        this.socket.send(a)
      }


      // if(e.data==="没有数据")
      //   alert("没有数据")
      // else if(e.data==="成功发送消息"){
      //   alert(e.data)
      // }
      // else{
      //   alert(e.data)
        // let dataJson = JSON.parse(e.data)
        // for(var i in dataJson){
        //
        //     console.log(dataJson[i].userId)
        //     console.log(dataJson[i].friendId)
        //     console.log(dataJson[i].lastTalkTime)
        //
        // }
      // }
    },


    open: function () {
      console.log("socket0连接成功")
    },
    error: function () {
      console.log("连接错误")
    },

    close: function () {
      console.log("socket0已经关闭")
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


