import { createApp } from "vue";
import App from "./App.vue";
import Antd from "ant-design-vue";
import "ant-design-vue/dist/reset.css";
import router from "./router";
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";

// 踩坑了，导入了两个组件库有样式冲突，我就先引入后面的一个，再引入前面的那一个，这样就会后面的样式会覆盖掉前面的样式
createApp(App)
  .use(router)
  .use(ArcoVue)
  .use(Antd)
  .use(mavonEditor)
  .mount("#app");
