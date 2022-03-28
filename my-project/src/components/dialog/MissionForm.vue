<template>
  <el-dialog
      :model-value="modelValue"
      :title="type==='project' ? '新建项目': '新建任务'"
      width="350px"
      :before-close="handleClose"
      :show-close="false"
  >

    <el-form label-position="top" ref="formRef" :model="form" label-width="120px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="负责人">
            <el-radio-group v-model="form.owner" size="small">
              <el-radio-button label="陈心桐">陈心桐</el-radio-button>
              <el-radio-button label="郭欣嘉">郭欣嘉</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="优先级">
            <el-radio-group v-model="form.priority" size="small">
              <el-radio-button label="low">低</el-radio-button>
              <el-radio-button label="medium">中</el-radio-button>
              <el-radio-button label="high">高</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="预计用时(H)">
            <el-row fullter="10">
              <el-col :span="6">
                <el-input v-model="estimatedCost" ></el-input>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="截止日期">
            <el-date-picker v-model="form.deadline" type="date" placeholder="选择截止日期"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="描述">
            <el-input v-model="form.description" type="textarea" :rows="4"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="$emit('update:modelValue', false)">Cancel</el-button>
        <el-button type="primary" @click="$emit('update:modelValue', onSubmit())">Confirm</el-button>
      </span>
    </template>
  </el-dialog>


  <image-cropper v-model="imageCropperVisible" @upload="cropperImgUpload"></image-cropper>
</template>

<script>
import axios from "axios";
import ImageCropper from "@/components/dialog/ImageCropper";



export default {

  components: {ImageCropper},
  inject:['reload'],
  props: ['modelValue', 'type', 'projectId'],
  data() {
    return {
      imageCropperVisible: false,
      form: {
        name: '',
        owner: '',
        priority: 'medium',
        estimatedCost: 6 * 60 * 60,
        deadline: new Date(),
        imgPath: "",
        description: "",
      },
    }
  },
  methods: {
    cropperImgUpload(data) {
      this.form.imgPath = data.imagePath
    },
    handleClose (done) {
      this.$emit('update:modelValue', false)
      done()
    },
    onSubmit(){
      this.form.deadline.setHours(23)
      this.form.deadline.setMinutes(59)
      this.form.deadline.setSeconds(59)
      axios.post(this.$serverUri + "/kanban/" + this.type, {
          name: this.form.name,
          description: this.form.description,
          estimatedCost: this.form.estimatedCost,
          deadline: this.form.deadline.getTime(),
          priority: this.form.priority,
          owner: this.form.owner,
          type: this.type,
          bizType: this.form.bizType,
          projectId: this.projectId,
          imgPath: this.form.imgPath
        }
      ).then(() => {
        this.reload()
      })
      return false
    }
  },
  watch: {
    modelValue(newVal) {
      if (newVal === true) {
        this.$axios.get("kanban/project/" + this.projectId).then(res => {
          this.form.owner = res.data.owner
          this.form.deadline = new Date(res.data.deadline)
        })
      }

    },
  },
  computed: {
    estimatedCost: {
      get() {
        return Math.round(this.form.estimatedCost/60/60 * 100) / 100
      },
      set(newValue) {
        return this.form.estimatedCost = newValue * 60 * 60
      }
    },
  },
  name: "MissionForm"
}
</script>

<style scoped>

</style>