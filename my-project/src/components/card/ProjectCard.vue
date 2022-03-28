<template>
  <el-card class="project-card" :body-style="{ padding: '0px' }">

    <el-row class="project-img">
      <router-link :to="'/project/' + projectId">
        <el-image
            class="project-img"
            :src="'/file/kanban?path=' + this.imgPath"
            fit="cover"
        ></el-image>
      </router-link>
    </el-row>

    <el-row class="project-row" justify="space-between" align="center">
      <el-col :span="20" :offset="1" style="font-size: 14px">{{name}}</el-col>
      <el-col :span="3">
        <el-dropdown>
          <el-icon>
            <more style="transform: rotate(90deg); cursor:pointer" />
          </el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item icon="edit" @click="this.kanbanEditDialogVisible = true">编辑</el-dropdown-item>

              <el-dropdown-item icon="delete" @click="deleteProject">删除</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>

    <ProjectEdit v-model="kanbanEditDialogVisible" :id="projectId"></ProjectEdit>
  </el-card>
</template>

<script>
import ProjectEdit from "@/components/dialog/ProjectEdit";

export default {
  props:["projectId", "name", "imgPath"],
  name: "ProjectCard",
  inject: ['reload'],
  components: {ProjectEdit},
  data() {
    return {
      kanbanEditDialogVisible: false
    }
  },
  methods: {
    deleteProject() {
      this.$axios.delete(this.$serverUri + "/kanban/project/" + this.projectId).then(() => {
        this.reload()
      })

    }
  }
}
</script>

<style scoped>
.project-card{
  border-radius: 15px;
  height: 158px;
  width: 254px;
}
.project-img{
  height: 120px;
  width: 254px;
}
.project-row{
  height: 38px;
  font-size: 14px;
  font-weight: bold;
  align-items: center;
}
</style>