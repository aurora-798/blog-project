<template>
  <a @click="showDrawer">View Profile</a>
  <a-drawer
    width="680px"
    placement="right"
    :closable="false"
    :open="open"
    @close="onClose"
  >
    <div>dsada</div>
  </a-drawer>
</template>
<script lang="ts" setup>
import { ref } from "vue";

const open = ref<boolean>(false);

const showDrawer = () => {
  open.value = true;
};
const onClose = () => {
  open.value = false;
};
</script>
