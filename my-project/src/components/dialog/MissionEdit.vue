<template>
  <el-dialog :model-value="modelValue" width="800px" :show-close="false" :before-close="handleClose" >
    <template #title>
      <el-space>
        <span style="font-weight: bold">
          {{kanban.name}}
        </span>
        <el-tag class="ml-2" :type="getStatusTagType()" effect="dark">{{this.kanban.status}}</el-tag>
      </el-space>
    </template>
    <el-space>
      <el-descriptions class="margin-top" :column="2"  border>
        <template #title>
          <el-button type="primary" icon="check" circle @click="editKanban"></el-button>
          <el-button type="primary" icon="close" circle @click="$emit('update:modelValue', false)"></el-button>
        </template>

        <template #extra>
          <el-button type="primary" icon="delete" circle @click="onDelete"></el-button>
        </template>

        <el-descriptions-item span="2">
          <template #label>
            <div class="cell-item">
              名称
            </div>
          </template>
          <el-input v-model="kanban.name" :disabled="disabled"></el-input>
        </el-descriptions-item>

        <el-descriptions-item span="1">
          <template #label>
            <div class="cell-item">
              负责人
            </div>
          </template>
          <el-radio-group v-model="kanban.owner" size="small" :disabled="disabled">
            <el-radio-button label="陈心桐"></el-radio-button>
            <el-radio-button label="郭欣嘉"></el-radio-button>
          </el-radio-group>
        </el-descriptions-item>

        <el-descriptions-item >
          <template #label>
            <div class="cell-item">
              优先级
            </div>
          </template>
          <el-radio-group v-model="kanban.priority" size="small" :disabled="disabled">
            <el-radio-button label="low">低</el-radio-button>
            <el-radio-button label="medium">中</el-radio-button>
            <el-radio-button label="high">高</el-radio-button>
          </el-radio-group>
        </el-descriptions-item>

        <el-descriptions-item >
          <template #label>
            <div class="cell-item">
              预计(时)
            </div>
          </template>
          <el-input v-model="estimatedCost" :disabled="disabled"></el-input>
        </el-descriptions-item>

        <el-descriptions-item >
          <template #label>
            <div class="cell-item">
              实际(时)
            </div>
          </template>
          <el-input v-model="actualCost" disabled></el-input>
        </el-descriptions-item>

        <el-descriptions-item span="2">
          <template #label>
            <div class="cell-item">
              截止日期
            </div>
          </template>

          <el-space>
            <el-date-picker  v-model="pickDate" type="date" placeholder="选择截止日期" :disabled="disabled"></el-date-picker>
            <el-link v-if="!disabled" type="primary" style="font-size: 6px" @click="setDeadline(1)">一天</el-link>
            <el-link v-if="!disabled" type="primary" style="font-size: 6px" @click="setDeadline(3)">三天</el-link>
            <el-link v-if="!disabled" type="primary" style="font-size: 6px" @click="setDeadline(10)">十天</el-link>
            <el-link v-if="!disabled" type="primary" style="font-size: 6px" @click="setDeadline(30)">三十天</el-link>
          </el-space>

        </el-descriptions-item>

        <el-descriptions-item span="2">
          <template #label>
            <div class="cell-item">
              描述
            </div>
          </template>
          <el-input v-model="kanban.description" type="textarea" :rows="4"></el-input>
        </el-descriptions-item>
      </el-descriptions>

      <el-space direction="vertical" alignment="flex-start">
        <el-timeline>
          <el-timeline-item :timestamp="getTime(kanban.createTime)" type="warning" icon="sunrise" v-if="kanban.createTime">
            {{getDay(kanban.createTime)}}
          </el-timeline-item>
          <el-timeline-item :timestamp="getTime(kanban.startTime)" type="primary" icon="sunset" v-if="kanban.startTime">
            {{getDay(kanban.startTime)}}
          </el-timeline-item>
          <el-timeline-item :timestamp="getTime(kanban.endTime)" type="success" icon="check" v-if="kanban.endTime">
            {{getDay(kanban.endTime)}}
          </el-timeline-item>
        </el-timeline>
        <el-timeline>
          <el-timeline-item :timestamp="getTime(kanban.deadline)" type="danger" icon="star" >
            {{getDay(kanban.deadline)}}
          </el-timeline-item>
        </el-timeline>
      </el-space>
    </el-space>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      deadlineTmp: {},
      disabled: false,
      kanban: {},
    }
  },
  inject:['reload'],
  name: "MissionEdit",
  props:['id', 'modelValue'],
  methods: {
    handleClose(){
      this.$emit('update:modelValue', false)
    },
    onDelete(){
      this.$axios.delete(this.$serverUri + "/kanban/mission/" + this.kanban.id).then(() => {
        this.reload()
      });
    },
    getDay(timestamp) {
      if (timestamp) {
        const date = new Date(timestamp)
        return date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日";
      }
      return ''
    },
    getTime(timestamp) {
      if (timestamp) {
        const date = new Date(timestamp)
        return date.getHours() + "时" + date.getMinutes() + "分" + date.getSeconds() + "秒"
      }
      return ''
    },
    setDeadline(day) {
      const date = new Date(new Date().getTime() + day * 24 * 60 * 60 * 1000)
      date.setHours(23)
      date.setMinutes(59)
      date.setSeconds(59)
      this.kanban.deadline = date

    },
    editKanban() {
      this.$axios.put("/kanban/mission", this.kanban).then(()=>{
        this.$emit('update:modelValue', false)
        this.reload()
      })
    },
    getStatusTagType() {
      if (this.kanban.status === 'todo') {
        return 'warning'
      }
      if (this.kanban.status === 'doing') {
        return 'primary'
      }
      if (this.kanban.status === 'done') {
        return 'success'
      }
    }
  },
  computed: {
    pickDate: {
      get() {
        return this.kanban.deadline
      },
      set(newValue) {
        newValue.setHours(23)
        newValue.setMinutes(59)
        newValue.setSeconds(59)
        this.kanban.deadline = newValue
      }
    },
    estimatedCost: {
      get() {
        return Math.round(this.kanban.estimatedCost/60/60 * 100) / 100
      },
      set(newValue) {
        return this.kanban.estimatedCost = newValue * 60 * 60
      }
    },
    actualCost: {
      get() {
        return Math.round(this.kanban.actualCost/60/60 * 100) / 100
      },
      set(newValue) {
        return this.kanban.actualCost = newValue * 60 * 60
      }
    }
  },
  watch: {

    modelValue(newVal) {
      if (newVal===true) {
        this.$axios.get("/kanban/mission/" + this.id).then((res) => {
          this.kanban = res.data
          this.disabled = res.data.status === "done"
        })
      }
    }
  }

}
</script>

<style scoped>
.cell-item{
  font-size: 8px;
  width: 56px;
}
</style>