<template>
  <!--  搜索框-->
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
        <router-link
          to="/login"
          class="admin-button"
          style="margin-right: 32px; text-decoration: none; color: inherit"
        >
          <span
            v-show="user.token == undefined && user.userRole == undefined"
            style="color: coral"
            >登录</span
          >
        </router-link>
        <router-link
          to="/man"
          class="admin-button"
          @click="goToManagerPage"
          style="text-decoration: none; color: inherit"
        >
          <span
            v-show="user.userRole == 'admin' && user.token != undefined"
            style="color: coral"
            >管理中心</span
          >
        </router-link>
      </div>
    </span>
  </a-layout-header>
  <a-layout-content style="width: 65%; margin: 3% auto; min-height: 100vh">
    <!--Tab栏-->
    <a-tabs
      v-model:activeKey="activeKey"
      @change="OnTabChange"
      size="large"
      type="line"
    >
      <a-tab-pane key="front" tab="前端">
        <PostList :post-list="postList"></PostList>
      </a-tab-pane>
      <a-tab-pane key="backend" tab="后端">
        <PostList :post-list="postList"></PostList>
      </a-tab-pane>
      <a-tab-pane key="post" tab="帖子">
        <PostList :post-list="postList"></PostList>
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片" force-render>
        <PictureList :image-list="imageList"></PictureList>
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :user-list="userList"></UserList>
      </a-tab-pane>
    </a-tabs>
  </a-layout-content>
  <a-layout-footer
    :style="footerStyle"
    class="footer"
    style="
      box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2) !important;
      margin: 25px auto 0px auto;
      text-align: center;
    "
    ><span style="margin: 0 auto"
      >某云提供强大数据访问支持; 版权所有，侵权必究!</span
    >
  </a-layout-footer>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import { useRoute, useRouter } from "vue-router";
import myAxIos from "@/plugins/MyAxIos";
import type { CSSProperties } from "vue";

const router = useRouter();
const route = useRoute();
const activeKey = ref(route.params.category);
const user = JSON.parse(localStorage.getItem("changge") || "{}");
const postList = ref([]);

const goToManagerPage = () => {
  // 使用路由导航跳转到ManagerPage组件
  router.push("/manager");
};

const footerStyle: CSSProperties = {
  textAlign: "center",
  color: "#000",
  backgroundColor: "white",
  height: "80px",
};

const userList = ref([]);

const imageList = ref([]);

const initSerarchParams = {
  text: "",
  pageSize: 10,
  pageNum: 1,
};
const searchParams = ref(initSerarchParams);
let KEYS: any = ref("front");
const OnTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
  // 这里的key就是post、picture、user
  // console.log("key:" + key);

  //更新KEYS,很关键
  KEYS.value = key;
  myAxIos
    .post("/common/active/vo", {
      // 哈哈，我也不知道怎么回事，加上...后key这个参数就包含在了json数据中了，hh
      //{text: "", pageSize: 10, pageNum: 1, key: "picture"}
      ...initSerarchParams,
      key,
    })
    .then((res) => {
      if (key == "post" || key == "backend" || key == "front") {
        postList.value = res.postVoList;
      } else if (key == "picture") {
        imageList.value = res.pictureList;
      } else if (key == "user") {
        userList.value = res.userList;
      }
    });
};
const onSearch = () => {
  router.push({
    query: searchParams.value,
  });
  //此处需要把key传来，然后针对某个具体的tab进行查询，因此我们定义了一个KEYS来保存我们此时选中的状态
  if (
    KEYS.value == "post" ||
    KEYS.value == "front" ||
    KEYS.value == "backend"
  ) {
    myAxIos
      .post("/post/page", {
        text: searchParams.value.text,
        pageNum: searchParams.value.pageNum,
        pageSize: searchParams.value.pageSize,
        category: KEYS.value,
      })
      .then((res) => {
        postList.value = res.postVoList;
      });
  } else if (KEYS.value == "picture") {
    myAxIos
      .post("/picture/all", {
        text: searchParams.value.text,
        pageNum: searchParams.value.pageNum,
        pageSize: searchParams.value.pageSize,
      })
      .then((res) => {
        imageList.value = res.pictureList;
        console.log("图片:" + imageList.value);
      });
  } else if (KEYS.value == "user") {
    myAxIos
      .post("/user/all", {
        text: searchParams.value.text,
        pageNum: searchParams.value.pageNum,
        pageSize: searchParams.value.pageSize,
      })
      .then((res) => {
        userList.value = res.userList;
      });
  }
};

//获取Thumb的标志信息和Favour的标志信息
// myAxIos.get("/post/getThumb", {}).then((res) => {
//   console.log("Thumb的标志信息的标志信息:" + res);
// });
// myAxIos.get("/post/getFavour", {}).then((res) => {
//   console.log("Favour的标志信息:" + res);
// });

// 监听Url是否发生变化，发生变化后进行相应跳转
watchEffect(() => {
  searchParams.value = {
    ...initSerarchParams,
    text: route.query.text,
  } as any;
});

/**
 * 初始化数据
 * @param params
 */
if (activeKey.value != "front") activeKey.value = "front";
myAxIos
  .post("/post/page", {
    text: searchParams.value.text,
    pageNum: searchParams.value.pageNum,
    pageSize: searchParams.value.pageSize,
    category: KEYS.value,
  })
  .then((res) => {
    postList.value = res.postVoList;
  });
</script>

<style scoped>
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

.footer {
  display: flex;
  margin: 0 auto;
  height: 80px;
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

.admin-button-container {
  margin-left: 10px; /* 调整按钮与搜索框之间的间距 */
  /* 如果需要更多样式，可以添加其他样式规则 */
}

.admin-button {
}

@media only screen and (min-device-width: 768px) {
  .sear {
    width: 85%;
  }
}
</style>
