<template>
  <div id="root">
    <PageCenter></PageCenter>
  </div>
</template>

<script lang="ts">
import PageCenter from "@/pages/PageCenter.vue";
import { defineComponent } from "vue";

export default defineComponent({
  components: {
    PageCenter,
  },
  setup() {
    const debounce = (callback: (...args: any[]) => void, delay: number) => {
      let tid: any;
      return function (...args: any[]) {
        const ctx = self;
        tid && clearTimeout(tid);
        tid = setTimeout(() => {
          callback.apply(ctx, args);
        }, delay);
      };
    };

    const _ = (window as any).ResizeObserver;
    (window as any).ResizeObserver = class ResizeObserver extends _ {
      constructor(callback: (...args: any[]) => void) {
        callback = debounce(callback, 20);
        super(callback);
      }
    };
  },
});
</script>

<style>
#root {
  width: 100%;
}
</style>
