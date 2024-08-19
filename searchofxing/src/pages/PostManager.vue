<template>
  <div style="background: white">
    <a-table
      :columns="columns"
      :data-source="data"
      style="margin: 0 auto"
      :resizable="true"
      size="large"
      v-if="data.length > 0"
    >
      <template #headerCell="{ column }">
        <template v-if="column.key === 'Title'">
          <span> Title </span>
        </template>
      </template>
      <!--    record取出来的就是每一项-->
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'Title'">
          <router-link
            :to="{ name: 'LookPostView', params: { id: record.id } }"
            style="padding-left: 10px; font-size: 17px"
            >{{ record.title }}
          </router-link>
          <!--          <a>-->
          <!--            {{ record.title }}-->
          <!--          </a>-->
        </template>
        <!--      Title-->
        <template v-if="column.key === 'introduce'">
          <span>
            {{ record.introduce }}
          </span>
        </template>
        <!--      Tags列表-->
        <template v-else-if="column.key === 'tags'">
          <span>
            <a-tag
              v-for="tag in record.tags"
              :key="tag"
              :color="
                tag === 'loser'
                  ? 'volcano'
                  : tag.length > 5
                  ? 'geekblue'
                  : 'green'
              "
            >
              {{ tag }}
            </a-tag>
          </span>
        </template>
        <template v-if="column.key === 'thumbNum'">
          <span>
            {{ record.thumbNum }}
          </span>
        </template>
        <template v-if="column.key === 'favourNum'">
          <span>
            {{ record.favourNum }}
          </span>
        </template>
        <template v-if="column.key === 'isDelete'">
          <span>
            {{ record.isDelete }}
          </span>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space wrap>
            <a-button type="dashed" size="small" @click="showDrawer(record.id)">
              <span>Edit</span>
            </a-button>
            <a-button
              type="dashed"
              @click="showDeleteConfirm(record.id)"
              size="small"
              >Delete
            </a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
  <p style="background-color: white; text-align: right">&nbsp;</p>
  <div>
    <a-drawer
      width="1124px"
      placement="left"
      :closable="false"
      :open="open"
      @close="onClose"
      :forceRender="true"
      :get-container="false"
      :destroyOnClose="true"
      v-if="open"
    >
      <div>
        <!--      菜单-->
        <div>
          <a-layout-header
            style="
              margin-bottom: 4px;
              background-color: white;
              padding-inline: 2px !important;
            "
          >
            <a-menu
              theme="light"
              mode="horizontal"
              v-model:selectedKeys="selectedKeys"
            >
              <a-menu-item key="1">基本内容</a-menu-item>
              <a-menu-item key="2">详细内容</a-menu-item>
            </a-menu>
            <!-- 调试输出 -->
          </a-layout-header>
        </div>
        <!--      表单-->
        <div v-show="selectedKeys[0] === '1'" style="margin-top: 30px">
          <a-form layout="horizontal">
            <!--      标题-->
            <a-form-item label="帖子title">
              <input class="input-title" v-model="Title" placeholder="标题：" />
              <span style="margin-left: 30px">文章分类：</span>
              <a-space>
                <a-select
                  v-model:value="selectValue"
                  style="width: 100px"
                  :options="options"
                  @change="handleChange"
                  placement="bottomRight"
                  :firstActiveValue="selectValue"
                >
                  <template #suffixIcon>
                    <smile-outlined class="ant-select-suffix" />
                  </template>
                </a-select>
              </a-space>
            </a-form-item>
            <!--      标签-->
            <a-form-item label="帖子Tags">
              <a-input-tag
                v-model="Tags"
                :style="{ width: '320px' }"
                placeholder="标签："
                size="medium"
                :max-tag-count="3"
              />
            </a-form-item>
            <!--      简介-->
            <a-form-item label="帖子简介">
              <a-space direction="vertical" size="large">
                <textarea
                  class="input-textarea"
                  v-model="Tarea"
                  placeholder="简介："
                />
              </a-space>
            </a-form-item>
            <a-form-item label="点赞数量">
              <input class="input-title" v-model="thumb" placeholder="标题：" />
            </a-form-item>
            <a-form-item label="收藏数量">
              <input
                class="input-title"
                v-model="favour"
                placeholder="标题："
              />
            </a-form-item>
            <!--      帖子提交-->
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
                  @click="submitData(postId)"
                  ><span style="">提交数据</span>
                </a-button>
              </div>
            </a-form-item>
          </a-form>
        </div>
        <div v-show="selectedKeys[0] == '2'" style="height: 100vh">
          <mavon-editor
            v-model="text"
            :toolbars="tools"
            style="height: 100%"
            :scrollStyle="true"
            :ishljs="true"
            placeholder="宝剑锋从磨砺出,梅花香自苦寒来....(请开始你的创作)"
            :tabSize="2"
          />
        </div>
      </div>
    </a-drawer>
  </div>
</template>
<script lang="ts" setup>
import { createVNode, ref } from "vue";
import myAxIos from "@/plugins/MyAxIos";
import { message, Modal } from "ant-design-vue";
import { Message } from "@arco-design/web-vue";
import { ExclamationCircleOutlined } from "@ant-design/icons-vue";
import axios from "axios";

import { SmileOutlined } from "@ant-design/icons-vue";
import type { SelectProps } from "ant-design-vue";

const user = JSON.parse(localStorage.getItem("changge") || "{}");
const selectValue = ref("分类");
const options = ref<SelectProps["options"]>([
  {
    value: "front",
    label: "前端",
  },
  {
    value: "backend",
    label: "后端",
  },
  {
    value: "post",
    label: "帖子",
  },
  {
    value: "picture",
    label: "图片",
  },
  {
    value: "user",
    label: "用户",
  },
]);

// 用户选择分类后执行该方法
const handleChange = (value: string) => {
  // console.log(value);
  selectValue.value = value;
};

// 定义表格列的类型
interface TableColumn {
  title: string;
  key: string;
}

const columns: TableColumn[] = [
  {
    title: "Title",
    key: "Title",
  },
  {
    title: "Tags",
    key: "tags",
  },
  {
    title: "introduce",
    key: "introduce",
  },
  {
    title: "thumbNum",
    key: "thumbNum",
  },
  {
    title: "favourNum",
    key: "favourNum",
  },
  {
    title: "isDelete",
    key: "isDelete",
  },
  {
    title: "Action",
    key: "action",
  },
];
const selectedKeys = ref<string[]>(["1"]); // 右侧

const postId = ref(0); //当前展开帖子的id值
const text = ref("");
const Title = ref("");
const Tags = ref([]);
const Tarea = ref("");
const thumb = ref(0);
const favour = ref(0);

const data = ref([]);

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
const submitData = (id: number) => {
  axios
    .put(
      "http://101.42.240.114:8888/searchApi/post/PutPost",
      {
        id: id,
        content: text.value,
        title: Title.value,
        tags: Tags.value,
        introduce: Tarea.value,
        thumbNum: thumb.value,
        favourNum: favour.value,
        category: selectValue.value,
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
const loadData = () => {
  myAxIos
    .post(
      "/post/all",
      {},
      {
        headers: {
          token: user.token,
        },
      }
    )
    .then((res) => {
      data.value = res;
    });
};
const DeleteData = (id: number) => {
  axios
    .delete("http://101.42.240.114:8888/searchApi/post/delete", {
      data: {
        id: id,
      },
      headers: {
        token: user.token,
      },
    })
    .then((res) => {
      if (res.data.code == 1) {
        loadData();
        Message.success("操作成功");
      } else {
        message.info("你的操作过于频繁,请稍候再试");
      }
    });
};

loadData();
const showDeleteConfirm = (id: number) => {
  // console.log("id:" + id);
  Modal.confirm({
    title: "Are you sure delete this post?",
    icon: createVNode(ExclamationCircleOutlined),
    content: "危险操作，确认操作吗?",
    okText: "确认",
    okType: "danger",
    cancelText: "取消",
    // 用户点击了确定后
    onOk() {
      DeleteData(id);
      // console.log("当前帖子的id为:" + id);
    },
    // 用户点击了取消后
    onCancel() {
      message.success("操作已撤销");
    },
  });
};

// 用于控制编辑组件的开启与关闭
const open = ref<boolean>(false);
const showDrawer = (id: number) => {
  // console.log("id的值为:" + id);
  setTimeout(() => {
    open.value = true;
  }, 100); // 延迟打开抽屉

  //回显数据请求
  axios
    .get("http://101.42.240.114:8888/searchApi/post/getPost", {
      params: {
        id: id,
      },
      headers: {
        token: user.token,
      },
    })
    .then((res) => {
      if (res.data.code == 1) {
        postId.value = res.data.data.id;
        text.value = res.data.data.content;
        Title.value = res.data.data.title;
        Tags.value = res.data.data.tags;
        Tarea.value = res.data.data.introduce;
        thumb.value = res.data.data.thumbNum;
        favour.value = res.data.data.favourNum;
        selectValue.value = res.data.data.category;
      } else message.error(res.data.msg);
    });
};
const onClose = () => {
  selectedKeys.value = ["1"]; // 重置selectedKeys
  open.value = false;
};
</script>
