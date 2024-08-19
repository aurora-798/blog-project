import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "../pages/IndexPage.vue";
import ManagerPage from "../pages/ManagerPage.vue"; // 导入ManagerPage组件
import LookPostView from "../pages/LookPostView.vue";
import UserLogin from "../pages/UserLogin.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home",
  },
  {
    path: "/home",
    component: IndexPage,
  },
  {
    path: "/login",
    name: "login",
    component: UserLogin,
  },
  {
    path: "/:category",
    component: IndexPage,
  },
  {
    path: "/manager", // 添加管理中心的路由规则
    name: "man",
    component: ManagerPage,
  },
  {
    path: "/postView/:id(\\d+)",
    name: "LookPostView",
    component: LookPostView,
  },
  {
    path: "/404",
    name: "ErrorPage",
    component: () => import("@/pages/ErrorPage.vue"),
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
