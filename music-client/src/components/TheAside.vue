<template>
  <transition name="slide-fade">
    <div class="the-aside" v-if="showAside">
      <h2 class="title">播放列表</h2>
      <ul class="menus">
        <li
          v-for="(item, index) in listPlayList"
          :key="index"
          :class="{ 'is-play': id == item.id }"
          @click="
            toplay(
              item.id,
              item.url,
              item.pic,
              index,
              item.name,
              item.lyric,
              item.isVip
            )
          "
        >
          {{ replaceFName(item.name) }}
        </li>
      </ul>
    </div>

    <!-- <el-drawer
          title="播放列表"
          :visible.sync="showAside">
          <ul class="menus">
                <li v-for="(item,index) in listPlayList" :key="index" :class="{'is-play': id==item.id}"
                @click="toplay(item.id,item.url,item.pic,item.index,item.name,item.lyric)">
                    {{replaceFName(item.name)}}
                </li>
            </ul>
        </el-drawer> -->
  </transition>
</template>
<script>
import { mapGetters } from "vuex";
import { getCollectOfUserId, isadd, setPlayCount, isSongadd, setSongPlayCount, addSongPlayCount } from "../api/index";

export default {
  name: "the-aside",
  computed: {
    ...mapGetters([
      "showAside", //是否显示播放中的歌曲列表
      "listOfSongs", //当前歌曲列表
      "id", //播放中的音乐id
      "loginIn", //用户是否已登录
      "userId", //当前登录用户的id
      "isActive", //当前播放的歌曲是否已收藏
      "listPlayList",
      "isNeedVip", //当前歌曲是否需要vip 1:是 0:否
      "isvip", //用户是否vip 1:是 0:否
    ]),
  },
  mounted() {
    let _this = this;
    document.addEventListener(
      "click",
      function () {
        _this.$store.commit("setShowAside", false);
      },
      true
    );
  },
  methods: {
    //提示信息
    notify(title, type) {
      this.$notify({
        title: title,
        type: type,
      });
    },
    //获取名字前半部分--歌手名
    replaceLName(str) {
      let arr = str.split("-");
      return arr[0];
    },
    //获取名字后半部分--歌名
    replaceFName(str) {
      let arr = str.split("-");
      return arr[1];
    },
    //播放
    toplay: function (id, url, pic, index, name, lyric, isVip) {
      if (this.isvip == 0 && isVip == 1) {
        this.notify("该歌曲需要开通VIP,才能播放", "warning");
        return;
      }
      let songListId = this.$route.params.id;
      if (this.userId) {
        isadd(this.userId, songListId).then((res) => {
          console.log("res==" + res);
          if (res) {
            setPlayCount(this.userId, songListId).then((res1) => {
              if (res1) {
                console.log("count成功加一");
              } else {
                console.log("count加一失败");
              }
            });
          } else {
            console.log("当前歌单id：" + songListId);
            var params = new URLSearchParams();
            params.append("userId", this.userId);
            params.append("songListId", songListId);
            params.append("playCount", 1);
            addPlayCount(params).then((res2) => {
              if (res2.code == 1) {
                console.log("歌单播放量添加成功");
              } else {
                console.log("歌单播放量添加失败");
              }
            });
          }
        });
        isSongadd(this.userId, id).then((res) => {
          console.log("res==" + res);
          if (res) {
            setSongPlayCount(this.userId, id).then((res1) => {
              if (res1) {
                console.log("songCount成功加一");
              } else {
                console.log("songCount加一失败");
              }
            });
          } else {
            console.log("当前歌曲id：" + id);
            var params = new URLSearchParams();
            params.append("userId", this.userId);
            params.append("songId", id);
            params.append("songPlayCount", 1);
            addSongPlayCount(params).then((res2) => {
              if (res2.code == 1) {
                console.log("歌曲播放量添加成功");
              } else {
                console.log("歌曲播放量添加失败");
              }
            });
          }
        });
      }
      console.log(index);
      this.$store.commit("setId", id);
      this.$store.commit("setUrl", this.$store.state.configure.HOST + url);
      this.$store.commit("setPicUrl", this.$store.state.configure.HOST + pic);
      this.$store.commit("setListIndex", index);
      this.$store.commit("setTitle", this.replaceFName(name));
      this.$store.commit("setArtist", this.replaceLName(name));
      this.$store.commit("setLyric", this.parseLyric(lyric));
      this.$store.commit("setIsActive", false);
      console.log(this.listOfSongs);
      this.$store.commit("setListPlayList", this.listOfSongs);
      if (this.loginIn) {
        getCollectOfUserId(this.userId).then((res) => {
          for (let item of res) {
            if (item.songId == id) {
              this.$store.commit("setIsActive", true);
              break;
            }
          }
        });
      }
    },
    //解析歌词
    parseLyric(text) {
      let lines = text.split("\n"); //将歌词按行分解成数组
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; //时间格式的正则表达式
      let result = []; //返回值
      //对于歌词格式不对的直接返回
      if (!/\[.+\]/.test(text)) {
        return [[0, text]];
      }
      //去掉前面格式不正确的行
      while (!pattern.test(lines[0])) {
        lines = lines.slice(1);
      }
      //遍历每一行，形成一个每行带着俩元素的数组，第一个元素是以秒为计算单位的时间，第二个元素是歌词
      for (let item of lines) {
        let time = item.match(pattern); //存前面的时间段
        let value = item.replace(pattern, ""); //存后面的歌词
        for (let item1 of time) {
          let t = item1.slice(1, -1).split(":"); //取出时间，换算成数组
          if (value != "") {
            result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]);
          }
        }
      }
      //按照第一个元素--时间--排序
      result.sort(function (a, b) {
        return a[0] - b[0];
      });
      return result;
    },
  },
};
</script>
<style  lang="scss" scoped>
@import "../assets/css/the-aside.scss";
.menu {
  height: 300px;
  overflow-y: hidden;
}
</style>
