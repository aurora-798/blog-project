<template>
  <!-- 竖排样式列表-->
  <a-list
    item-layout="vertical"
    size="large"
    :pagination="pagination"
    :data-source="props.postList"
  >
    <template #renderItem="{ item }">
      <a-list-item key="item.title">
        <!--        标题内容-->
        <!-- 此处可以在标题下方添加内容 <a-list-item-meta :description="item.content">-->
        <a-list-item-meta>
          <!--          头像-->

          <template #avatar>
            <a-avatar :src="item.url" size="large" />
            <router-link
              :to="{ name: 'LookPostView', params: { id: item.id } }"
              style="padding-left: 10px; font-size: 17px"
              >{{ item.title }}
            </router-link>
            <hr width="100%" />
            <a-space
              v-for="(it, index) of item.tags"
              :key="index"
              wrap
              :size="[0, 'large']"
            >
              <a-tag color="red" v-if="index % 4 == 0">{{ it }}</a-tag>
              <a-tag color="green" v-else-if="index % 4 == 1">{{ it }}</a-tag>
              <a-tag color="purple" v-else-if="index % 4 == 2">{{ it }}</a-tag>
              <a-tag color="cyan" v-else>{{ it }}</a-tag>
            </a-space>
          </template>
        </a-list-item-meta>
        {{ item.introduce }}
        <!--        点赞、收藏功能-->
        <template #actions>
          <div style="font-size: 15px" class="findTime">
            发布时间 : {{ item.createTime }}
          </div>
          <!--          旋转小星星-->
          <!--          <star-outlined style="color: red" spin="true" />-->
          <span
            v-for="{ icon, index } in actions"
            :key="icon"
            @click="() => handleActionClick(item, index)"
          >
            <!--           组件地址: https://antdv.com/components/comment-cn#api-->
            <LikeOutlined
              v-if="index == 1"
              style="margin-right: 3px; font-size: 16px; cursor: pointer"
              class="OutLine"
            ></LikeOutlined>
            <StarOutlined
              v-if="index == 2"
              style="margin-right: 5px; font-size: 16px; cursor: pointer"
              class="OutLine"
            ></StarOutlined>
            <!--            根据index来区分是哪一个按钮-->
            <span v-if="index == 1" class="Num">{{ item.thumbNum }}</span>
            <span v-else-if="index == 2" class="Num">{{ item.favourNum }}</span>
            <!--            <span v-else>0</span>-->
          </span>
        </template>
      </a-list-item>
    </template>
  </a-list>
</template>
<script lang="ts" setup>
import { defineProps, onMounted, withDefaults } from "vue";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});

const actions: Record<string, any>[] = [
  // 点赞按钮LikeOutlined
  { icon: LikeOutlined, index: 1 },
  // 收藏按钮
  { icon: StarOutlined, index: 2 },
  // 留言按钮
  // { icon: MessageOutlined, index: 3 },
];
// let thumbFlag = ref(0);
// let favourFlag = ref(0);
const user = JSON.parse(localStorage.getItem("changge") || "{}");
const handleActionClick = (item: any, index: any) => {
  //执行点赞操作
  // 其他操作逻辑类似
  if (user.token != undefined) {
    if (index === 1) {
      // 用户点击了第一个按钮，向后端发送请求,执行点赞操作
      myAxIos
        .post(
          "/post/updateThumbNum",
          {
            id: item.id,
            thumbNum: item.thumbNum,
            userId: item.userId,
          },
          {
            headers: {
              token: user.token,
            },
          }
        )
        .then((res) => {
          item.thumbNum = res.thumbNum;
          // thumbFlag = res.flag;
        });
    }
    // 执行收藏操作
    else if (index === 2) {
      // 用户点击了第二个按钮，向后端发送请求,执行收藏操作
      myAxIos
        .post(
          "/post/updateFavourNum",
          {
            id: item.id,
            favourNum: item.favourNum,
            userId: item.userId,
          },
          {
            headers: {
              token: user.token,
            },
          }
        )

        .then((res) => {
          // console.log("收藏后的数据：" + res);
          item.favourNum = res.favourNum;
          // favourFlag = res.flag;
        });
    } else {
      // 用户点击了第二个按钮，向后端发送请求,执行收藏操作
      myAxIos.post("/post/message", {}).then((res) => {
        console.log(res);
      });
    }
  } else {
    router.push("/login");
    message.info("请登录");
  }
};
import { StarOutlined, LikeOutlined } from "@ant-design/icons-vue";
import myAxIos from "@/plugins/MyAxIos";
import router from "@/router";
import { message } from "ant-design-vue";

const pagination = {
  onChange: (page: number, pageSize: number) => {
    // console.log("current=:" + page, "page=:" + pageSize);
    //获取所有的帖子数据
    // myAxIos
    //   .post("/post/page", {
    //     pageNum: page,
    //     pageSize: pageSize,
    //   })
    //   .then((res: any) => {
    //     console.log("分页查询结果显示:" + res);
    //   });
  },
  //每次查询的条数
  pageSize: 5,
};
</script>

<style>
/*有bug，干脆不加*/
/*.compThumb {*/
/*  color: red;*/
/*}*/

/*.compFavour {*/
/*  color: red;*/
/*}*/
a {
  /*text-decoration: none;*/
  font-size: 18px;
  color: blue;
}

/*电脑样式*/
/*当屏幕宽度至少为1000px像素时，具有类名.OutLine和.findTime的元素将具有以下样式*/
@media only screen and (min-device-width: 1000px) {
}

/*适用于我的x80手机*/
/*屏幕宽度至少为320像素。*/
/*屏幕宽度不超过480像素。*/
@media screen and (min-width: 320px) and (max-width: 480px) {
  /*点赞收藏按钮*/
  .OutLine {
    margin-right: 1px;
    font-size: 11px !important;
    cursor: pointer;
    color: #42b983;
  }

  /*帖子发布时间*/
  .findTime {
    font-size: 11px !important;
  }

  .ant-list-item-action-split {
    display: none !important;
  }

  .Num {
    font-size: 10px;
  }
}
</style>
