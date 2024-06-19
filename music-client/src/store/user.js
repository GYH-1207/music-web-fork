const user = {
  state: {
    userId: '',                 //用户id
    username: '',               //用户账号
    avator: '',               //用户头像
    isvip: '',                //是否vip 1:是 0:否
    validUntil: '',           //有效期截至日期
    No3: [],
    stySongList: [],
    otherList: []
  },
  getters: {
    userId: state => {
      let userId = state.userId
      if (!userId) {
        userId = JSON.parse(window.sessionStorage.getItem('userId'))
      }
      return userId
    },
    username: state => {
      let username = state.username
      if (!username) {
        username = JSON.parse(window.sessionStorage.getItem('username'))
      }
      return username
    },
    avator: state => {
      let avator = state.avator
      if (!avator) {
        avator = JSON.parse(window.sessionStorage.getItem('avator'))
      }
      return avator
    },
    isvip: state => {
      let isvip = state.isvip
      if (!isvip) {
        isvip = JSON.parse(window.sessionStorage.getItem('isvip'))
      }
      return isvip
    },
    validUntil: state => {
      let validUntil = state.validUntil
      if (!validUntil) {
        validUntil = JSON.parse(window.sessionStorage.getItem('validUntil'))
      }
      return validUntil
    },
    No3: state => {
      let No3 = state.No3;
      if (!No3.length) {
        No3 = JSON.parse(window.sessionStorage.getItem('No3') || null);
      }
      return No3;
    },
    stySongList: state => {
      let stySongList = state.stySongList;
      if (!stySongList.length) {
        stySongList = JSON.parse(window.sessionStorage.getItem('stySongList') || null);
      }
      return stySongList;
    },
    otherList: state => {
      let otherList = state.otherList;
      if (!otherList.length) {
        otherList = JSON.parse(window.sessionStorage.getItem('otherList') || null);
      }
      return otherList;
    },

  },
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId
      window.sessionStorage.setItem('userId', JSON.stringify(userId))
    },
    setUsername: (state, username) => {
      state.username = username
      window.sessionStorage.setItem('username', JSON.stringify(username))
    },
    setAvator: (state, avator) => {
      state.avator = avator
      window.sessionStorage.setItem('avator', JSON.stringify(avator))
    },
    setIsVip: (state, isvip) => {
      state.isvip = isvip
      window.sessionStorage.setItem('isvip', JSON.stringify(isvip))
    },
    setValidUntil: (state, validUntil) => {
      state.validUntil = validUntil
      window.sessionStorage.setItem('validUntil', JSON.stringify(validUntil))
    },
    setNo3: (state, No3) => {
      state.No3 = No3;
      window.sessionStorage.setItem('No3', JSON.stringify(No3));
    },
    setStySongList: (state, stySongList) => {
      state.stySongList = stySongList;
      window.sessionStorage.setItem('stySongList', JSON.stringify(stySongList));
    },
    setOtherList: (state, otherList) => {
      state.otherList = otherList;
      window.sessionStorage.setItem('otherList', JSON.stringify(otherList));
    },
  }
}

export default user