<template>
  <div>
    <a-layout-header>
      <span class="input">
        <div class="input-search">
          <a-input-search
            placeholder="这只是一个摆设hh"
            enter-button
            size="large"
            style="width: 55%; margin: 0 auto"
            class="sear"
            @search="onSearch"
            :value="text"
            @update:value="text = $event"
          />
          <router-link
            to="/login"
            class="admin-button"
            style="margin-right: 32px; text-decoration: none; color: inherit"
          >
            <span style="color: coral">登录</span>
          </router-link>
        </div>
      </span>
    </a-layout-header>
    <section>
      <!-- 登录框 -->
      <div class="container">
        <div class="form">
          <h2 style="color: #4a90e2">登录</h2>
          <form autocomplete="off">
            <div id="login" class="form-group">
              <input
                type="text"
                required="required"
                placeholder="用户名"
                name="u"
                class="form-input"
                v-model="username"
              />
              <br />
              <input
                type="password"
                required="required"
                placeholder="密码"
                name="p"
                class="form-input"
                v-model="password"
              />
              <button
                class="but"
                type="button"
                @click="Submit()"
                style="margin-top: 20px"
              >
                登录
              </button>
              <p class="forget">
                没有账户?<a @click="openAfter()" style="cursor: default"
                  >注册</a
                >
              </p>
            </div>
          </form>
        </div>
      </div>
    </section>
  </div>
</template>
<script lang="ts" setup>
import { nextTick, ref } from "vue";
import { message } from "ant-design-vue";
import axios from "axios";
import router from "@/router";

const username = ref("");
const password = ref("");

const Submit = () => {
  axios
    .post("http://101.42.240.114:8888/searchApi/login", {
      userAccount: username.value,
      userPassword: password.value,
    })
    .then((res) => {
      if (res.data.code == 1) {
        if (res.data.data.userName == "邢壑") {
          message.success("长歌管理员，恭喜您登录成功~~");
        } else {
          message.success("登录成功");
        }
        localStorage.setItem("changge", JSON.stringify(res.data.data));
        // 使用路由导航跳转到ManagerPage组件
        router.push("/home");
      } else {
        message.error(res.data.msg);
      }
    });
};

const text = ref("");
const onSearch = async () => {
  await nextTick(); // 等待下一个事件循环周期
  text.value = "";
  message.info("这只是一个摆设，将来也许会实现哈哈");
};

const openAfter = () => {
  message.info("以后再说，也许一辈子~~~");
};
</script>
<style scoped>
.container {
  width: 400px;
  min-height: 400px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  box-shadow: 0 25px 45px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.5);
  border-right: 1px solid rgba(255, 255, 255, 0.2);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);

  display: flex; /* 使用 flex 布局 */
  flex-direction: column; /* 垂直布局元素 */
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
}

.but {
  background-color: #50617a;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.but:hover {
  background-color: #6eb1ff;
}

.form {
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  max-width: 400px; /* 控制表单的最大宽度 */
  width: 100%; /* 表单宽度占满父容器 */
  margin: 0 auto; /* 实现水平居中 */
  display: flex; /* 使用 flex 布局 */
  flex-direction: column; /* 垂直布局元素 */
  justify-content: center; /* 垂直居中 */
  align-items: center; /* 水平居中 */
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 10px; /* 控制元素之间的垂直间距 */
}

h2 {
  color: #363e4a;
  margin-bottom: 20px; /* 控制标题与表单之间的间距 */
}

section {
  /* 相对定位 */
  position: relative;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  /* linear-gradient() 函数用于创建一个表示两种或多种颜色线性渐变的图片 */
  background: linear-gradient(to bottom, #f1f4f9, #dff1ff);
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
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

.form-input {
  width: 100%;
  padding: 10px;
  margin: 5px 0;
  border: none !important;
  border-radius: 4px;
  outline: none;
}

.forget {
  margin-top: 10px;
  font-size: 14px;
  color: #363e4a;
}

.forget a {
  text-decoration: none;
  color: #4a90e2;
}

.forget a:hover {
  text-decoration: underline;
}
</style>
