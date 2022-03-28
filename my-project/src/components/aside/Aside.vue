<template>
  <el-image class="logo" :src="this.kanbanImgUrl" fit="cover" v-if="!this.collapse"></el-image>
  <el-image class="logo" :src="this.kanbanImgUrl" fit="cover" v-if="this.collapse" style="width: 25px; height: 25px"></el-image>

  <el-menu class="menu"
           default-active="1"
           :router=true
           text-color="white"
           active-text-color="white"
           background-color="#404961"
  >
    <el-menu-item class="menu-item" index="/overview" :style="this.menuItemStyle">
      <el-icon ><Grid /></el-icon>
      <span  v-if="!this.collapse">概览</span>
    </el-menu-item>
    <el-menu-item class="menu-item" index="/project-group" :style="this.menuItemStyle">
      <el-icon ><Monitor /></el-icon>
      <span  v-if="!this.collapse">全部项目</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/mission-group" :style="this.menuItemStyle">
      <el-icon ><Aim /></el-icon>
      <span  v-if="!this.collapse">全部任务</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/frame/portainer" :style="this.menuItemStyle">
      <el-icon ><Goblet /></el-icon>
      <span  v-if="!this.collapse">容器管理</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/frame/fileBrowser" :style="this.menuItemStyle">
      <el-icon ><Folder /></el-icon>
      <span  v-if="!this.collapse">文件管理</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/frame/docsify" :style="this.menuItemStyle">
      <el-icon ><Document /></el-icon>
      <span  v-if="!this.collapse">文档</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/frame/transmission" :style="this.menuItemStyle">
      <el-icon ><Download /></el-icon>
      <span  v-if="!this.collapse">下载</span>
    </el-menu-item>

    <el-menu-item class="menu-item" index="/frame/homeAssistant" :style="this.menuItemStyle">
      <el-icon ><House /></el-icon>
      <span  v-if="!this.collapse">家庭助手</span>
    </el-menu-item>


  </el-menu>

  <el-button class="option-button" v-if="!this.collapse" size="small" icon="DArrowLeft" @click="asideOptionHandler"></el-button>
  <el-button class="option-button" v-if="this.collapse" size="small" icon="DArrowRight" @click="asideOptionHandler"></el-button>




</template>

<script>
import kanbanLog from '@/assets/kanban.png'
export default {
  data() {
    return {
      kanbanImgUrl: kanbanLog,
      collapse: false,
      menuItemStyle: {
        paddingLeft: '20px'
      }
    }
  },
  name: "Aside",
  methods: {
    asideOptionHandler() {
      if (this.collapse === false) {
        this.collapse = true
        this.menuItemStyle.paddingLeft='5px'
        this.$emit("widthChange", {width: '34px'})
      } else {
        this.collapse = false
        this.menuItemStyle.paddingLeft='20px'
        this.$emit("widthChange", {width: '140px'})
      }
    },
  }
}
</script>

<style scoped>
.logo {
  background-color: #404961;
  height: 64px;
  width: 64px;
  margin: 0 auto;
  display: block;
}
.menu {
  border: 0;
}
.menu-item {
  width: 140px;
}
.option-button {
  width: 12px;
  position: absolute;
  left: 5px;
  bottom: 10px
}
</style>