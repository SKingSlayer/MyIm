<template>
  <div class="nav-contain">
    <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <AsideBarItem v-for="router in routers"
                    :key="router.path"
                    :router="router"
      >
      </AsideBarItem>
    </el-menu>
  </div>
</template>
