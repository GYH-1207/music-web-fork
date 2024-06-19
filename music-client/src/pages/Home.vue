<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item, index) in songsList" :key="index">
      <div class="section-title">{{ item.name }}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper";
import contentList from "../components/ContentList";
import { mapGetters } from "vuex";
import {
  getAllSinger,
  getAllSongList,
  selectByUserId,
  selectBySongListId,
  getSongListOfLikeStyle,
  selectOther,
} from "../api/index";
export default {
  name: "home",
  components: {
    Swiper,
    contentList,
  },
  computed: {
    ...mapGetters(["userId", "No3", "stySongList", "otherList"]),
  },
  data() {
    return {
      songsList: [
        { name: "推荐歌单", list: [] },
        { name: "歌手", list: [] },
      ],
      No3style: [],
      songlistbox: [],
      ooo: [],
    };
  },
  created() {
    this.getSongList();
    this.getSinger();
  },
  methods: {
    async getSongList() {
      if (this.userId) {
        try {
          // 获取用户播放量前三的歌单风格
          const userSongLists = await selectByUserId(this.userId);
          const stylesSet = new Set();
          for (const userSong of userSongLists) {
            const songStyles = await selectBySongListId(userSong.songListId);
            songStyles.forEach((style) => stylesSet.add(style.style));
          }
          const userStyles = Array.from(stylesSet);
          this.$store.commit("setNo3", userStyles);

          // 根据用户喜好推荐歌单
          const stylePromises = userStyles.map((style) =>
            getSongListOfLikeStyle(style)
          );
          const styleSongLists = await Promise.all(stylePromises);

          let combinedSongList = styleSongLists.flat();
          this.$store.commit("setStySongList", combinedSongList);

          // 补充其他推荐歌单
          const existingListLength = combinedSongList.length;
          const additionalLength = Math.max(0, 10 - existingListLength);
          const allSongLists = await getAllSongList();
          const additionalSongs = allSongLists
            .filter((song) => !userStyles.includes(song.style))
            .slice(0, additionalLength);

          this.$store.commit("setOtherList", additionalSongs);

          // 合并推荐歌单
          combinedSongList = combinedSongList.concat(additionalSongs);
          this.songsList[0].list = combinedSongList;
        } catch (err) {
          console.error("Error fetching song list:", err);
        }
      } else {
        getAllSongList()
          .then((res) => {
            this.songsList[0].list = res.slice(0, 10);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    getSinger() {
      //获取前十名歌手
      getAllSinger()
        .then((res) => {
          this.songsList[1].list = res.slice(0, 10);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/home.scss";
</style>
