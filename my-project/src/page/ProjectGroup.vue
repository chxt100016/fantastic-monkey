<template>
  <el-container>
    <el-header style="height: 30px">
      <el-space>
        <el-checkbox-group v-model="this.type" size="small">
          <el-checkbox-button key="live" label="live">生活</el-checkbox-button>
          <el-checkbox-button key="learn" label="learn">学习</el-checkbox-button>
          <el-checkbox-button key="work" label="work">工作</el-checkbox-button>
        </el-checkbox-group>

        <el-button size="small" type="primary" icon="DocumentAdd" @click="dialogVisible = true"></el-button>
      </el-space>




    </el-header>
    <el-main>
      <el-space wrap>
        <template v-for="(item, index) in projectArr" :key="index">
          <ProjectCard :name="item.name" :project-id="item.id" :img-path="item.imgPath"></ProjectCard>
        </template>
      </el-space>

      <ProjectForm v-model="dialogVisible"></ProjectForm>
    </el-main>
  </el-container>


</template>

<script >
import ProjectCard from "../components/card/ProjectCard";
import axios from "axios";
import ProjectForm from "@/components/dialog/ProjectForm";

export default {
  data() {
    return {
      type: [],
      dialogVisible: false,
      projectArr: [],
      name: "学习vue",
    }
  },
  name: "ProjectGroup",
  components: {ProjectForm, ProjectCard},

  methods: {
    getProject() {
      axios.get(this.$serverUri + "/kanban/project", {
        params: {
          'type': this.type.join(",")
        }
      }).then((response) => {
        this.projectArr = response.data
      })
    }
  },

  watch: {
    type() {
      this.getProject()
    }
  },

  mounted() {
    this.getProject()
  },


}
</script>

<style scoped>

</style>