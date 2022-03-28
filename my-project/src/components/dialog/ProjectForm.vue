<template>
  <el-dialog :model-value="modelValue" width="350px" :before-close="handleClose" :show-close="false">
    <template #title>
      <div class="title">新建项目</div>
    </template>
    <template #default>
      <el-form label-position="top" ref="formRef" :model="form" label-width="120px" >
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目名称">
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
            <el-form-item label="类型">
              <el-radio-group v-model="form.type" size="small">
                <el-radio-button label="study">学习</el-radio-button>
                <el-radio-button label="work">工作</el-radio-button>
                <el-radio-button label="live">生活</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="封面图片">
              <el-button class="ml-3" type="primary" @click="this.imageCropperVisible = true" icon="picture" size="small">
                图片
              </el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止日期">
              <el-date-picker
                  v-model="form.deadline"
                  type="date"
                  placeholder="选择截止日期"
                  style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="描述">
              <el-input v-model="form.description" type="textarea" :rows="4"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </template>

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
  props: ['modelValue'],
  data() {
    return {
      imageCropperVisible: false,
      form: {
        name: '',
        owner: '',
        type: 'live',
        estimatedCost: 12 * 60 * 60,
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
      axios.post(this.$serverUri + "/kanban/project", {
          name: this.form.name,
          description: this.form.description,
          estimatedCost: this.form.estimatedCost,
          deadline: this.form.deadline.getTime(),
          owner: this.form.owner,
          type: this.form.type,
          bizType: this.form.bizType,
          imgPath: this.form.imgPath
        }
      ).then(() => {
        this.reload()
      })
      return false
    }
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
  name: "ProjectForm"
}
</script>

<style scoped>


</style>