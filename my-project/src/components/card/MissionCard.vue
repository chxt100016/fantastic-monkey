<template>
  <el-card class="mission-card" shadow="hover">

    <el-row class="title" :gutter="10" align="middle">
      <el-col :span="24">{{ getName }}</el-col>
    </el-row>

    <el-row class="tag" :gutter="10" justify="space-between">
      <el-col :span="6">
        <el-tag type="info" size="small">{{currentTime}}</el-tag>
      </el-col>
      <el-col :span="3.5">
        <el-button :type="getButtonType()" size="small"  circle>{{getOwner}}</el-button>
      </el-col>
    </el-row>

    <el-row class="tag" :gutter="10">
      <el-col>
        <el-tag type="info" size="small" >截止时间: {{getDeadline}}</el-tag>
      </el-col>
    </el-row>

  </el-card>
</template>

<script>

export default {
  props:['name', 'description', 'owner', 'cost', 'status', 'createTime', 'startTime', 'endTime', 'deadline'],
  name: "MissionCard",
  methods: {
    getButtonType() {
      if (this.status === "todo") {
        return 'warning'
      }
      if (this.status === "doing") {
        return 'primary'
      }
      if (this.status === "done") {
        return 'success'
      }
    }
  },
  computed: {
    currentTime() {
      let timeDesc = ''
      let date
      if (this.status === 'todo') {
        timeDesc = "创建时间: "
        date = new Date(this.createTime);
      }
      if (this.status === 'doing') {
        timeDesc = "开始时间: "
        date = new Date(this.startTime);
      }
      if (this.status === 'done') {
        timeDesc = "完成时间: "
        date = new Date(this.endTime);
      }
      let dateStr = ''
      let now = new Date()
      if (date.getFullYear() === now.getFullYear()) {
        dateStr = (date.getMonth()+1) + "月" + date.getDate() + "日"
      } else {
        dateStr = date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日"
      }
      return timeDesc + dateStr
    },

    getDeadline(){
      let date = new Date(this.deadline)
      let now = new Date()
      if (date.getFullYear() === now.getFullYear()) {
        return (date.getMonth()+1) + "月" + date.getDate() + "日"
      } else {
        return date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日"
      }
    },

    getOwner() {
      return this.owner[this.owner.length - 1]
    },

    getName() {
      if (this.name.length <= 25) {
        return this.name
      } else {
        return this.name.slice(0,26) + "......"
      }
    },
  }
}
</script>

<style scoped>
.mission-card{
  border-radius: 6px;
  width: 240px;
  height: 90px;
  margin-bottom: 7px;
}
.title {
  font-size: 12px;
  font-weight: bold;
  height: 17px;
  color: #303133;
  margin-bottom: 3px;
}
.tag {
  margin-bottom: 2px;
}


</style>