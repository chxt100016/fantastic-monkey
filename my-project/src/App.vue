<template>
  <el-container class="container">
<!--    <el-aside class="aside" :style="asideStyle">-->
    <el-aside class="aside" :style="asideStyle">
      <Aside  @widthChange="onWidthChange"></Aside>
    </el-aside>
    <el-main class="main">
      <router-view v-if="isRouterAlive"></router-view>
    </el-main>
  </el-container>


</template>

<script>
import Aside from "./components/aside/Aside";
export default {
  components: {
    Aside
  },

  provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  data() {
    return{
      asideStyle: {
        width: '140px',
      },
      isRouterAlive: true                    //控制视图是否显示的变量
    }
  },
  methods: {
    onWidthChange(data) {
      console.log(data.width)
      this.asideStyle.width = data.width

    },
    reload () {
      this.isRouterAlive = false;            //先关闭，
      this.$nextTick(function () {
        this.isRouterAlive = true;         //再打开
      })
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;

  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;

}

.container {
  height: 100vh;
  width: 100vw;
}

.aside {
  background-color: #404961;

}

.main {
  background-color: #EFF3FB;
  padding-left: 0;
}

.el-dialog__header {
  border-bottom: 2px solid #EFF3FB;
}

.el-dialog__body {
  padding-top: 10px;
  padding-bottom: 5px;
}

.el-card__body{
  padding: 12px
}
</style>
