<template>
  <a-list
    item-layout="horizontal"
    :data-source="props.imageList"
    :grid="{ gutter: 20, xs: 2, sm: 2, md: 2, lg: 2, xl: 2, xxl: 2 }"
  >
    <template #renderItem="{ item }">
      <!-- 添加样式设置上下和左右间距为 10px -->
      <a-card hoverable size="small" style="margin: 10px 10px 0 10px">
        <template #cover>
          <!--          当图片无法正常显示时显示无法加载图片-->
          <img
            style="width: 100%; height: 200px; object-fit: cover"
            :src="item.url"
            title="hh,版权所有，侵权必究~~"
            alt="原创作者已删除该图片"
            onerror="this.src='https://www.91ajs.com/content-data/upload/202303/202303071603195893.png'"
          />
        </template>
        <a-card-meta :title="item.title"></a-card-meta>
      </a-card>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps, ref, onMounted } from "vue";

interface Props {
  imageList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  imageList: () => [],
});
</script>

<style scoped></style>
