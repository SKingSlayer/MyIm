<template>
  <div>
    <el-container>
      <el-header></el-header>
      <el-container>
        <el-aside >
          <div>
                <el-menu
                  default-active="2"
                  class="el-menu-vertical-demo"
                  @open="handleOpen"
                  @close="handleClose"
                  background-color="#545c64"
                  text-color="#fff"
                  active-text-color="#ffd04b">
                  <el-submenu index="1">
                    <template slot="title">
                      <i class="el-icon-s-platform"></i>
                      <span>好友</span>
                    </template>
                    <el-menu-item-group>
                      <router-link to="/Success/AppMain/chat">
                        <el-menu-item index="1-1">在场服务</el-menu-item>
                      </router-link>
                      <router-link to="/Success/AppMain/chat1">
                        <el-menu-item index="1-2">历史服务</el-menu-item>
                      </router-link>
                    </el-menu-item-group>
                  </el-submenu>
                  <el-submenu index="2">
                    <template slot="title">
                      <i class="el-icon-s-tools"></i>
                      <span>系统设置</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="1-1">权限管理</el-menu-item>
                      <el-menu-item index="1-2">角色管理</el-menu-item>
                    </el-menu-item-group>
                  </el-submenu>
                  <el-submenu index="3">
                    <template slot="title">
                      <i class="el-icon-user-solid"></i>
                      <span>人员数据</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="1-1">人员数量</el-menu-item>
                      <el-menu-item index="1-2">人员位置</el-menu-item>
                    </el-menu-item-group>
                  </el-submenu>
                  <el-submenu index="4">
                    <template slot="title">
                      <i class="el-icon-message-solid"></i>
                      <span>健康报警</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="1-1">心率报警</el-menu-item>
                      <el-menu-item index="1-2">血压报警</el-menu-item>
                    </el-menu-item-group>
                  </el-submenu>
                </el-menu>
          </div>
        </el-aside>
        <el-main>
          <AppMain></AppMain>
        </el-main>
      </el-container>
    </el-container>
  </div>

</template>
<script>
import AppMain from "./AppMain";

export default {
  name: 'mynav',
  components: {
    AppMain
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height:660px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 600px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
