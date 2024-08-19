<template>
  <a-list
    item-layout="horizontal"
    :data-source="props.userList"
    :grid="{ gutter: 16, xs: 2, sm: 2, md: 2, lg: 2, xl: 2, xxl: 2 }"
  >
    <template #renderItem="{ item }">
      <a-card hoverable size="small" style="margin: 10px 10px 0 10px">
        <template #cover>
          <img
            alt="example"
            :src="item.userAvator"
            style="width: 100%; height: 200px; object-fit: cover"
          />
        </template>
        <a-card-meta :title="item.userName">
          <template #description>{{ item.userProfile }}</template>
        </a-card-meta>
        <div style="padding-bottom: 20px"></div>
      </a-card>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  userList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  userList: () => [],
});
</script>

<style scoped></style>
