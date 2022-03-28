<template>
  <el-carousel class="carousel" :interval="8000" type="card" height="270px">
    <el-carousel-item v-for="item in this.alarmArr" :key="item">
      <el-image
          class="img"
          :src="'http://2202.com' + item.imgPath"
          fit="fill"
      ></el-image>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  data() {
    return {
      message: "qqqq!!",
      alarmArr:[],
    }
  },
  name: "Overview",
  mounted() {
    const begin = new Date()
    begin.setHours(0)
    begin.setMinutes(0)
    begin.setSeconds(0)

    const end = new Date()
    end.setHours(23);
    end.setMinutes(59);
    end.setSeconds(59)
    this.$axios.get("/alarm", {
      params: {
        'beginTime': begin.getTime(),
        'endTime': end.getTime(),
      }
    }).then((res) => {
      this.alarmArr = res.data
    })
  }
}
</script>

<style scoped>
.carousel {
  width: 100%;
}
.img {
  width: 480px;
  height: 270px;
}
</style>