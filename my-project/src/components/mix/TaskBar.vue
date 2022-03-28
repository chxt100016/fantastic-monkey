<template>
  <el-space direction="vertical" :size="1" alignment="start">
    <el-badge :value="this.data.length" class="item" :type="type">
      <el-card class="mission-title">
        <el-row justify="space-between">
          <el-col :span="6">{{title}}</el-col>
          <el-col :span="6">
            <el-button v-if="addButton" :type="type" size="small" @click="dialogVisible = true" icon="DocumentAdd" ></el-button>
          </el-col>
        </el-row>
        <el-progress class="mission-type-percentage" :percentage="percentage" :status="type" :show-text="false" />
      </el-card>
    </el-badge>
    <el-scrollbar class="scrollbar">
      <draggable v-model="this.innerData" :group="this.group"  @add="updateMissionStatus($event)" item-key="id">
        <template #item="{element}">
          <MissionCard class="mission-content" v-bind="element" @click="onClickMission(element)"></MissionCard>
        </template>
        <template #footer>
          <div class="footer"></div>
        </template>
      </draggable>
    </el-scrollbar>
  </el-space>

  <MissionForm v-model="dialogVisible" type="mission" :project-id="this.$route.params.id"></MissionForm>
  <MissionEdit v-model="kanbanEditDialogVisible" :id="clickMissionId"></MissionEdit>
</template>

<script>
import draggable from "vuedraggable";
import MissionCard from "@/components/card/MissionCard";
import MissionForm from "@/components/dialog/MissionForm";
import MissionEdit from "@/components/dialog/MissionEdit";

export default {
  data() {
    return {
      innerData: this.data,
      clickMissionId: undefined,
      kanbanEditDialogVisible: false,
      dialogVisible: false,
    }
  },
  components: {draggable, MissionCard, MissionForm, MissionEdit},
  props: ['data', 'total', 'group', 'status', 'addButton'],
  name: "TaskBar",
  methods: {
    onClickMission(item) {
      this.clickMissionId = item.id
      this.kanbanEditDialogVisible = true
    },
    updateMissionStatus(event) {
      const index = event.newDraggableIndex
      this.innerData[index].status = this.status
      if (this.status === 'todo') {
        this.innerData[index].createTime = new Date().getTime()
        this.innerData[index].startTime = null
        this.innerData[index].endTime = null
      }
      if (this.status === 'doing') {
        this.innerData[index].startTime = new Date().getTime()
        this.innerData[index].endTime = null
      }
      if (this.status === 'done') {
        this.innerData[index].endTime = new Date().getTime()
      }
      this.$axios.put("/kanban/mission/status", this.innerData[index])
    }
  },
  computed: {
    type() {
      if ('todo' === this.status) return "warning";
      if ('doing' === this.status) return "primary";
      if ('done' === this.status) return "success";
      return "danger";
    },
    title() {
      if ('todo' === this.status) return "TODO";
      if ('doing' === this.status) return "DOING";
      if ('done' === this.status) return "DONE";
      return ""

    },
    percentage() {
      return Math.round((this.data.length/this.total) * 100)
    }
  },
  watch: {
    data(newVal) {
      this.innerData = newVal
    }
  }
}
</script>

<style scoped>
.mission-title {
  width: 240px;
  height: 75px;
  margin-bottom: 15px;
}
.footer {
  width: 240px;
  height: 20px;
  margin-bottom: 5px;
}
.mission-type-percentage{
  height: 30px;
}
.scrollbar{
  height: calc(100vh - 200px);
  min-height: 400px;
  width: 250px;
  border-radius: 8px;
}
</style>