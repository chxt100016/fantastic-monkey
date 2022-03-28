<template>
  <el-dialog :model-value="modelValue" width="350px" :before-close="handleClose">
    <vueCropper v-if="this.originalImgBase64"
        style="width: 300px; height: 300px"
        ref="cropper"
        :img="this.originalImgBase64"
        :outputSize="option.size"
        :outputType="option.outputType"
        :info="true"
        :full="option.full"
        :canMove="option.canMove"
        :canMoveBox="option.canMoveBox"
        :fixedBox="option.fixedBox"
        :original="option.original"
        :autoCrop="option.autoCrop"
        :autoCropWidth="option.autoCropWidth"
        :autoCropHeight="option.autoCropHeight"
        :centerBox="option.centerBox"
        :high="option.high"
        :infoTrue="option.infoTrue"
        @realTime="realTime"
        @imgLoad="imgLoad"
        @cropMoving="cropMoving"
        :enlarge="option.enlarge"
    ></vueCropper>

    <el-upload
        ref="upload"
        class="upload-demo"
        action="/file/kanban"
        method="post"
        :limit="1"
        :auto-upload="false"
        :on-success="onUploadSuccess"
        :on-change="changeImg"
        :before-upload="beforeUpload"
        :http-request="uploadImage"
    >
      <template #trigger>
        <el-button type="primary" size="small" icon="documentAdd">
          选择
        </el-button>
      </template>
      <el-button class="ml-3" type="success" @click="submitUpload" size="small" icon="upload">
        上传
      </el-button>

      <template #tip>
        <div class="el-upload__tip" style="color: red">
          limit 1 file, new file will cover the old file
        </div>
      </template>
    </el-upload>
  </el-dialog>
</template>

<script>
import 'vue-cropper/dist/index.css'
import { VueCropper }  from "vue-cropper";

export default {
  data() {
    return {
      originalImg: undefined,
      originalImgBase64: undefined,
      option: {
        size: 1,
        full: false,
        outputType: 'png',
        canMove: true,
        fixedBox: false,
        original: false,
        canMoveBox: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 254,
        autoCropHeight: 120,
        centerBox: false,
        high: true,
        max: 99999,
      }
    }
  },
  name: "ImageCropper",
  props: ['modelValue', 'image'],
  emits: ['upload'],
  methods: {
    uploadImage() {
      this.$refs.cropper.getCropBlob(data => {
        let file = new File([data], this.originalImg.name, {type: data.type})
        let param = new FormData()
        param.append("file", file)
        let config = {
          headers: { "Content-Type": "multipart/form-data" }
        };
        this.$axios.post('/file/kanban', param, config).then((res)=>{
          this.$emit('upload', {imagePath: res.data})
          this.$emit('update:modelValue', false)
        })
      })

    },
    changeImg(file) {
      this.getBase64(file.raw).then(res => {
        this.originalImgBase64 = res
      });
      this.originalImg = file
    },
    getBase64(file) {
      return new Promise(function(resolve, reject) {
        let reader = new FileReader();
        let imgResult = "";
        reader.readAsDataURL(file);
        reader.onload = function() {
          imgResult = reader.result;
        };
        reader.onerror = function(error) {
          reject(error);
        };
        reader.onloadend = function() {
          resolve(imgResult);
        };
      });
    },

    onUploadSuccess(res){
      this.form.imgPath = res
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    handleClose (done) {
      this.$emit('update:modelValue', false)
      done()
    },
  },
  components:{VueCropper,}
}
</script>

<style scoped>

</style>