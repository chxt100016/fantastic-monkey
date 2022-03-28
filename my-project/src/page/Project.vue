<template>
  <el-page-header class="page-header" @back="this.$router.push('/project-group')" icon="ArrowLeft">
    <template #title>
      <span>back</span>
    </template>
    <template #content>
      <el-tag effect="dark" type="info">{{this.project_info.name}}</el-tag>
    </template>
  </el-page-header>

  <el-space  alignment="flex-start" :size="20" style="padding-left: 50px">
    <TaskBar group="taskbar" :data="TODO_ARR" :total="total" status="todo" :addButton="true"></TaskBar>
    <TaskBar group="taskbar" :data="DOING_ARR" :total="total" status="doing" :addButton="false"></TaskBar>
    <TaskBar group="taskbar" :data="DONE_ARR" :total="total" status="done" :addButton="false"></TaskBar>
  </el-space>
</template>

<script>
import axios from "axios";
import TaskBar from "@/components/mix/TaskBar";

export default {
  components: {TaskBar},
  name: "Project",
  data() {
    return {
      project_info: {},
      TODO_ARR: [],
      DOING_ARR: [],
      DONE_ARR: [],
    }
  },
  computed: {
    total() {
      return this.TODO_ARR.length + this.DOING_ARR.length + this.DONE_ARR.length;
    },
  },
  mounted() {
    axios.get(this.$serverUri + "/kanban/project/" + this.$route.params.id).then((response) => {
      this.project_info =  response.data
      this.TODO_ARR = response.data.todoList
      this.DOING_ARR = response.data.doingList
      this.DONE_ARR = response.data.doneList
    })
  },

  methods: {

  }
}
</script>

<style scoped>
.page-header {
  padding-left: 5px;
  padding-bottom: 20px;
}
</style>