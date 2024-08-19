import axios from "axios";

const user = JSON.parse(localStorage.getItem("changge") || "{}");
const instance = axios.create({
  baseURL: "http://101.42.240.114:8888/searchApi",
  timeout: 100000,
  headers: {
    "X-Custom-Header": "foobar",
    token: user.token,
  },
});
// 添加响应拦截器
instance.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    const data = response.data;
    if (data.code === 0) {
      return data.data;
    }
    return response.data;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);
export default instance;
