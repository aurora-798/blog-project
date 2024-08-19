<template>
  <div class="all">
    <!--  导航栏-->
    <a-layout-header>
      <span class="input">
        <div class="input-search">
          <a-input-search
            v-model:value="searchParams.text"
            placeholder="尽你所搜,懂得都懂hh"
            enter-button
            @search="onSearch"
            size="large"
            style="width: 55%; margin: 0 auto"
            class="sear"
          />
          <router-link to="/man" class="admin-button" @click="goToManagerPage">
            <span>管理中心</span>
          </router-link>
        </div>
      </span>
    </a-layout-header>
    <!--  大div-->
    <a-layout-content>
      <div class="marge">
        <!-- 左侧的空白区域 -->
        <!--      <div class="card">-->
        <!--        <div class="card1">-->
        <!--          <p>-->
        <!--            <eye-outlined />-->
        <!--            100+-->
        <!--          </p>-->
        <!--          <p>-->
        <!--            <EditOutlined />-->
        <!--            {{ data.updateTime }}-->
        <!--          </p>-->
        <!--        </div>-->

        <!--        <div class="card2">-->
        <!--          <p>霸道无双</p>-->
        <!--          <p style="text-align: right">宋代 李清照</p>-->
        <!--          <p>执剑江湖镇乾坤，</p>-->
        <!--          <p>霸气独尊意气豪。</p>-->
        <!--          <p>苍天为盾地为铠，</p>-->
        <!--          <p>世界难敌我刀刃。</p>-->
        <!--        </div>-->
        <!--      </div>-->
        <!--    主要笔记区域-->
        <div style="margin-top: 55px">
          <!--        <div class="content" style="margin-right: 6%; margin-left: 2%">-->
          <div class="content" style="margin: 0px auto; width: 90%">
            <mavon-editor
              v-model="text"
              :toolbars="tools"
              style="
                height: auto;
                font-size: 16px;
                color: #38b0de;
                padding: 10px;
                /* 自定义光标颜色 */
                cursor: none !important;
              "
              :subfield="false"
              :defaultOpen="'preview'"
              :toolbarsFlag="false"
              :editable="false"
              :scrollStyle="true"
              :ishljs="true"
              previewBackground="#ffffff"
              class="mav"
            />
          </div>
        </div>
      </div>
    </a-layout-content>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import myAxIos from "@/plugins/MyAxIos";
// import { EyeOutlined, EditOutlined } from "@ant-design/icons-vue";

const route = useRoute();
// 使用 computed 创建一个响应式的 id 变量，以访问 $route.params.id
const id = computed(() => {
  return route.params.id;
});
const text = ref("");
const preface = ref("");
const data = ref({});

import { onMounted } from "vue";

const router = useRouter();

const initSerarchParams = {
  text: "",
  pageSize: 10,
  pageNum: 1,
};
const searchParams = ref(initSerarchParams);

const onSearch = () => {
  alert("搜索框开始搜索");
};

const goToManagerPage = () => {
  // 使用路由导航跳转到ManagerPage组件
  router.push("/manager");
};

// 使用 onMounted 钩子
onMounted(() => {
  window.scrollTo(0, 0); // 将页面滚动到顶部
});

myAxIos
  .get("/post/getPost", {
    params: {
      id: id.value,
    },
  })
  .then((res) => {
    text.value = res.data.content;
    preface.value = res.data.introduce;
    data.value = res.data;
  });

const tools = ref({
  italic: true, // 斜体
  underline: true, // 下划线
  mark: true, // 标记
  superscript: true, // 上角标
  subscript: true, // 下角标
  quote: true, // 引用
  ol: true, // 有序列表
  ul: true, // 无序列表
  link: true, // 链接
  imagelink: true, // 图片链接
  table: true, // 表格
  fullscreen: true, // 全屏编辑
  readmodel: true, // 沉浸式阅读
  htmlcode: false, // 展示html源码
  help: true, // 帮助
  /* 1.3.5 */
  trash: true, // 清空
  save: true, // 保存（触发events中的save事件）
  /* 1.4.2 */
  navigation: true, // 导航目录
  /* 2.1.8 */
  alignleft: true, // 左对齐
  aligncenter: true, // 居中
  alignright: true, // 右对齐
  /* 2.2.1 */
  subfield: true, // 单双栏模式
  preview: true, // 预览
});
</script>

<!--只能是style，不然代码中有边框hh-->
<style>
.card {
  width: 100%;
  height: 100vh;
  position: sticky;
  top: 70px; /* 将卡片固定在距离顶部 50px 的位置 */
}

.card1 {
  width: 90%;
  height: auto;
  margin: 0 auto;
  text-align: center;
  font-size: 14px;
  color: blueviolet;
  border-radius: 10px; /* 圆角边框 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  transition: box-shadow 0.3s ease; /* 阴影效果的过渡动画 */
  background-color: #ffffff;
  padding: 20px;
}

.card2 {
  width: 90%;
  height: auto;
  margin: 20px auto;
  text-align: center;
  font-size: 14px;
  color: rosybrown;
  border-radius: 10px; /* 圆角边框 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  transition: box-shadow 0.3s ease; /* 阴影效果的过渡动画 */
  background-color: #ffffff;
  padding: 20px;
}

.markdown-body pre {
  padding: 0px !important;
  box-shadow: 1px 1px 7px #b4b4b4;
  margin-top: 6px !important;
}

/*设置高度自适应内容的高度*/
.mav {
  min-height: auto !important;
}

.introd {
  font-size: 14px !important;
  min-height: auto !important;
}

/*x80端*/
@media screen and (min-width: 320px) and (max-width: 480px) {
  .hljs {
    padding: 8px;
    font-size: 15px !important;
  }
}

/*代码设置*/
.hljs {
  background-color: #c7edcc !important;
  font-size: 17px !important;
  border-radius: 4px;
  padding: 10px;
}

/*设置编辑器的鼠标光标显示状态*/
.v-note-wrapper .v-note-panel .v-note-show .v-show-content,
.v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
  cursor: default !important;
  color: black !important;
}
</style>

<style scoped>
/*代码部分设置*/

/*电脑样式*/
/*当屏幕宽度至少为1000px像素时，具有类名.OutLine和.findTime的元素将具有以下样式*/
@media only screen and (min-device-width: 1000px) {
  .header {
    width: 100%;
    height: 80px;
    top: 0;
    left: 0;
    background: #fff;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    z-index: 1000;
  }

  .marge {
    background-color: #f2f2f2;
    /*display: grid;*/
    /*grid-template-columns: 1fr 80%;*/
    margin: 0px auto;
  }
}

/*适用于我的x80手机*/
/*屏幕宽度至少为320像素。*/
/*屏幕宽度不超过480像素。*/
@media screen and (min-width: 320px) and (max-width: 480px) {
  .card {
    display: none;
  }

  .header {
    background: linear-gradient(to right, #d1fdff 0%, #fddb92 100%);
    height: 44px;
  }

  .marge {
    background-color: #f2f2f2;
    width: 100%;
    height: 100vw;
    display: grid;
    grid-template-columns: 1fr 80%;
    margin: 20px auto;
  }
}

.input {
  display: flex;
  width: 100%;
  margin: 0 auto;
  height: 80px;
  position: sticky;
  padding-left: 5%;
  padding-right: 5%;
  top: 0;
  left: 0;
  align-items: center;
  justify-content: space-between; /* 使用 space-between 来将搜索框和按钮分散对齐 */
  background: #fff;
  box-sizing: border-box;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  z-index: 2;
}

.input-search {
  display: flex;
  align-items: center; /* 垂直居中 */
  width: 100%; /* 填充所有可用的宽度 */
}
</style>
