import { createApp } from 'vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


import App from './App.vue'
import {router} from "./router";
import * as icon from "@element-plus/icons-vue";
import axios from "axios";



const app = createApp(App)
// app.config.globalProperties.$serverUri = "http://192.168.1.230:8080"
app.config.globalProperties.$serverUri = ""
app.config.globalProperties.$axios = axios

for (let iconKey in icon) {
    app.component(iconKey, icon[iconKey])
}

app.use(ElementPlus)
    .use(router)
    .mount('#app')

