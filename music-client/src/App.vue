<template>
  <div id="app">
    <the-header />
    <router-view class="music-content" v-if="isRouterAlive" />
    <song-audio />
    <the-aside />
    <play-bar />
    <scroll-top />
    <the-footer />
  </div>
</template>

<script>
import TheHeader from "./components/TheHeader";
import ScrollTop from "./components/ScrollTop";
import TheFooter from "./components/TheFooter";
import SongAudio from "./components/SongAudio";
import TheAside from "./components/TheAside";
import PlayBar from "./components/PlayBar";
import { mapGetters } from "vuex";
import { getNewVipInfo } from "@/api/index";

export default {
  name: "App",
  provide() {
    return {
      reload: this.reload,
    };
  },
  data() {
    return {
      isRouterAlive: true,
    };
  },
  computed: {
    ...mapGetters(["userId"]),
  },
  components: {
    TheHeader,
    ScrollTop,
    TheFooter,
    SongAudio,
    TheAside,
    PlayBar,
  },
  created() {
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem("store")) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("store"))
        )
      );
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    // beforeunload事件在页面刷新时先触发
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    });
  },
  mounted() {
    // 在组件加载完成后，启动定时器
    this.startTimer();
  },
  methods: {
    // 启动定时器
    startTimer() {
      this.timer = setInterval(() => {
        this.updateVipInfo(); // 更新用户VIP信息
      }, 10000); 
    },
    // 更新用户VIP信息的方法，可以发送请求获取最新的VIP信息
    updateVipInfo() {
      getNewVipInfo(this.userId)
        .then((res) => {
          if (res.code == 1) {
            const json = JSON.parse(res.data);
            const validUntil = json.validUntil;

            //vip未过期
            this.$store.commit("setIsVip", 1);
            this.$store.commit("setValidUntil", validUntil);
          } else {
            //vip已过期
            this.$store.commit("setIsVip", 0);
            this.$store.commit("setValidUntil", "");
          }
        })
        .catch((err) => {
          console.log("更新失败");
        });
      console.log("更新用户VIP信息...");
    },
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(() => {
        this.isRouterAlive = true;
      });
    },
  },
};
</script>

<style  lang="scss" scoped>
@import "./assets/css/app.scss";
::-webkit-scrollbar {
  width: 0 !important;
}
::-webkit-scrollbar {
  width: 0 !important;
  height: 0;
}
</style>
