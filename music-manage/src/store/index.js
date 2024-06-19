import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex)

const store = new Vuex.Store({
  state:{
    HOST: 'http://127.0.0.1:8888',
    isPlay: false,      //是否播放中
    url: '/song/1651128642024myMusicTest1S.m4a',            //歌曲地址
    id: '',             //歌曲id
    currentPageStore: ''
  },
  getters: {
    isPlay: state => state.isPlay,
    url: state => state.url,
    id: state => state.id,
    currentPageStore: state => state.currentPageStore
  },
  mutations: {
    setIsPlay: (state,isPlay) => {state.isPlay = isPlay},
    setUrl: (state,url) => {state.url = url},
    setId: (state,id) => {state.id = id},
    setCurrentPageStore: (state,currentPageStore) => {state.currentPageStore = currentPageStore}
  }
})

export default store