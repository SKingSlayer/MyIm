<template>

<div id = "ap">
  <form  id = "a">
    <img src="/img/P.png" width="72" height="72">
    <h2 class="=form-signin0-heading">Please sign in</h2>
    <input class="form-control" v-model="userName" type="text" placeholder="用户名" ><br>
    <input class="form-control" v-model="userPassword" type="password" placeholder="密码"><br>
    <button class="btn btn-lg btn-primary btn-block" type="button" @click="myFunction" >Sign in</button>
    <button class="btn btn-lg btn-primary btn-block" type="button" @click="register" >Go to regist</button>
  </form>
</div>

</template>

<script>
import axios from 'axios'
export default {
  name: 'ap',
  data: function() {
    return {
      userName: '',
      userPassword: ''
    };
  },

  methods:{
    myFunction:function () {
      axios.post('http://localhost:8081/user/selectUserName', {
        userName: this.userName,
        userPassword: this.userPassword,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}   //跨域
      }).then(function (dat) {
        if (dat.data == '0')
          alert("用户不存在")
        else if (dat.data == '1')
          alert("登录失败，账号或密码错误")
        else if (dat.data == '2')
        {
          alert("Success")
          this.$router.replace('/Success')
        }
          //当前窗体跳转

        //新窗体跳转
        // window.open('/user/successLogin')
      }.bind(this)).catch(function () {
        console.log("传输失败")
      })
    },
    register:function () {
      console.log("传输失败")
      alert("用户不存在")
      window.location.href = "user/register"
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
#ap{
  margin-top: 150px;
}
</style>
