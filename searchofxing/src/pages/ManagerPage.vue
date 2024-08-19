<template>
  <div>
    <a-layout>
      <!-- 左侧侧边栏 -->
      <a-layout-sider
        breakpoint="lg"
        collapsed-width="0"
        @collapse="onCollapse"
        @breakpoint="onBreakpoint"
        style="background-color: whitesmoke"
      >
        <div class="logo">
          <SettingOutlined
            :spin="true"
            style="font-size: 30px; color: #42b983"
          ></SettingOutlined>
          <span style="font-size: 20px; margin-left: 10px">后台管理中心</span>
        </div>
        <a-menu
          v-model:selectedKeys="selectedKeys"
          theme="dark"
          mode="inline"
          @click="handleMenuClick"
          style="background: whitesmoke"
        >
          <div style="color: black; width: 80%; margin: 0 auto">
            <a-menu-item key="1">
              <EditOutlined></EditOutlined>
              <span class="nav-text"> 添加帖子</span>
            </a-menu-item>
            <a-menu-item key="2">
              <SettingOutlined />
              <span class="nav-text">帖子管理</span>
            </a-menu-item>
            <a-menu-item key="3">
              <PictureOutlined />
              <span class="nav-text">图片管理</span>
            </a-menu-item>
            <a-menu-item key="4">
              <UserOutlined />
              <span class="nav-text">用户管理</span>
            </a-menu-item>
          </div>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header
          style="background: whitesmoke; height: 66px !important"
        >
        </a-layout-header>
        <a-layout-content>
          <div>
            <!-- 使用动态组件来渲染不同的内容 -->
            <component :is="selectedComponent" />
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import { ref } from "vue";
import ComponentC from "@/components/ComponentC.vue";
import ComponentD from "@/components/ComponentD.vue";
import AddPost from "@/pages/AddPost.vue";
import PostManager from "@/pages/PostManager.vue";
import MyDiViDer from "@/components/MyDiViDer";
import { useRouter } from "vue-router";
import {
  FolderAddOutlined,
  SettingOutlined,
  UserOutlined,
  EditOutlined,
  PictureOutlined,
} from "@ant-design/icons-vue";
import { message } from "ant-design-vue";

export default {
  setup() {
    const router = useRouter(); // 导入router对象
    const user = JSON.parse(localStorage.getItem("changge") || "{}");

    if (!user.token || user.userRole !== "admin") {
      // 用户未登录或不具备管理员角色，重定向到登录页面
      message.error("请登录");
      router.push({ name: "login" });
    }
    const selectedKeys = ref(["1"]); // 默认选中第一个菜单项
    const selectedComponent = ref("AddPost");

    const onCollapse = (collapsed, type) => {
      console.log(collapsed, type);
    };

    const onBreakpoint = (broken) => {
      console.log(broken);
    };

    const handleMenuClick = (item) => {
      console.log(item.key);
      // 根据点击的菜单项设置选中的内容组件
      switch (item.key) {
        case "1":
          selectedComponent.value = "AddPost";
          break;
        case "2":
          selectedComponent.value = "PostManager";
          break;
        case "3":
          selectedComponent.value = "ComponentC";
          break;
        case "4":
          selectedComponent.value = "ComponentD";
          break;
        default:
          selectedComponent.value = "AddPost"; // 默认选择内容 A
      }
    };

    return {
      selectedKeys,
      selectedComponent,
      onCollapse,
      onBreakpoint,
      handleMenuClick,
      UserOutlined,
      EditOutlined,
      PictureOutlined,
    };
  },
  components: {
    MyDiViDer,
    AddPost,
    PostManager,
    ComponentC,
    ComponentD,
    FolderAddOutlined,
    SettingOutlined,
    UserOutlined,
    EditOutlined,
    PictureOutlined,
  },
};
</script>

<style>
/* 这里添加了一些样式 */
.logo {
  height: 32px;
  /*background: rgba(255, 255, 255, 0.2);*/
  margin: 16px;
}

.site-layout-sub-header-background {
  background: #fff;
}

.site-layout-background {
  background: #fff;
}

[data-theme="dark"] .site-layout-sub-header-background {
  background: #141414;
}

/*导航栏*/
li.ant-menu-item.ant-menu-item-selected {
  background: pink !important;
}

/*全局的灰白色，对所有的网页生效*/
body {
  background-color: #ecf8fa;
}

/*基本样式、详细内容调整行高*/
.ant-layout-header {
  height: 40px !important;
  line-height: 40px !important;
}
</style>
