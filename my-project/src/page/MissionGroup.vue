<template>
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
  name: "MissionGroup",
  data() {
    return {
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
    axios.get(this.$serverUri + "/kanban/mission/all").then((response) => {
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