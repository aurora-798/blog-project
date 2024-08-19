<template>
  <div id="app" style="margin: 0 auto; height: 800px">
    <a-layout class="layout" style="margin: 0 auto; background-color: #f6ffed">
      <a-layout-header style="margin-bottom: 4px; background-color: white">
        <a-menu
          theme="light"
          mode="horizontal"
          v-model:selectedKeys="selectedKey"
        >
          <a-menu-item key="1">基本内容</a-menu-item>
          <a-menu-item key="2">详细内容</a-menu-item>
        </a-menu>
      </a-layout-header>

      <a-layout-content style="height: 800px">
        <div v-show="selectedKey[0] === '1'" style="margin-top: 30px">
          <a-form layout="horizontal" style="max-width: 90%; margin: 0 auto">
            <a-form-item label="帖子title">
              <input class="input-title" v-model="Title" placeholder="标题：" />
            </a-form-item>
            <MyDiViDer></MyDiViDer>
            <MyDiViDer></MyDiViDer>
            <a-form-item label="帖子Tags">
              <a-input-tag
                v-model="Tags"
                :style="{ width: '320px' }"
                placeholder="标签："
                size="medium"
                :max-tag-count="3"
              />
            </a-form-item>
            <MyDiViDer></MyDiViDer>
            <MyDiViDer></MyDiViDer>
            <a-form-item label="帖子简介">
              <a-space direction="vertical" size="large">
                <textarea
                  class="input-textarea"
                  v-model="Tarea"
                  placeholder="简介："
                />
              </a-space>
            </a-form-item>
            <MyDiViDer></MyDiViDer>
            <MyDiViDer></MyDiViDer>
            <a-form-item label="帖子提交">
              <div>
                <a-button
                  type="dashed"
                  ghost
                  style="
                    width: 85px;
                    border-color: #42b983;
                    color: #42b983;
                    height: 30px;
                    line-height: 15px;
                  "
                  @click="submitData()"
                  ><span style="">提交数据</span>
                </a-button>
              </div>
            </a-form-item>
            <MyDiViDer></MyDiViDer>
            <MyDiViDer></MyDiViDer>
          </a-form>
        </div>
        <div v-show="selectedKey[0] == '2'" style="height: 100%">
          <mavon-editor
            v-model="text"
            :toolbars="tools"
            style="height: 100%"
            codeStyle="light"
            :scrollStyle="true"
            :ishljs="true"
            placeholder="宝剑锋从磨砺出,梅花香自苦寒来....(请开始你的创作)"
            :tabSize="2"
          />
        </div>
        <div v-show="selectedKey[0] == '3'">暂定</div>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import MyDiViDer from "@/components/MyDiViDer.vue";
import { message } from "ant-design-vue";
import axios from "axios";

const selectedKey = ref<string[]>(["1"]); // 右侧
const text = ref("");
const Title = ref("");
const Tags = ref([]);
const Tarea = ref("");

const user = JSON.parse(localStorage.getItem("changge") || "{}");
const submitData = () => {
  axios
    .post(
      "http://101.42.240.114:8888/searchApi/post/AddPost",
      {
        content: text.value,
        title: Title.value,
        tags: Tags.value,
        introduce: Tarea.value,
      },
      {
        headers: {
          token: user.token,
        },
      }
    )
    .then((res) => {
      if (res.data.code == 1) {
        message.success(res.data.msg);
        window.location.href = "http://172.17.116.195:8080/#/post";
      } else message.error(res.data.msg);
    });
};
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

<style>
.markdown-body pre {
  padding: 0 !important;
}

.hljs {
  background-color: rgb(242, 243, 245) !important;
  padding: 10px;
  font-size: 17px !important;
}

.input-title:focus {
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075),
    0 0 8px rgba(102, 175, 233, 0.6);
  outline-style: none;
  border: 1px solid #ccc;
}

.input-title {
  -webkit-appearance: none;
  width: 320px;
  height: 30px;
  outline: none; /*清除input点击之后的黑色边框*/
  border: 0;
  background-color: rgb(242, 243, 245);
  padding-right: 12px;
  padding-left: 12px;
}

.input-textarea {
  -webkit-appearance: none;
  width: 320px;
  height: 30px;
  outline: none; /*清除input点击之后的黑色边框*/
  border: 0;
  background-color: rgb(242, 243, 245);
  padding-right: 12px;
  padding-left: 12px;
  width: 320px;
  height: 200px;
}

.input-textarea:focus {
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075),
    0 0 8px rgba(102, 175, 233, 0.6);
  outline-style: none;
  border: 1px solid #ccc;
}
</style>
