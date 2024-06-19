const configure = {
    state:{
        HOST: 'http://127.0.0.1:8888',  //后台访问地址根目录
        activeName: '',                  //当前选中的菜单名
        showAside: false,               //是否显示播放中的歌曲列表
        loginIn: false,                 //用户是否已登录
        isActive: false,                //当前歌曲是否已收藏
        activeOrder: false,             //当前选中的订单名
        srcUrl: ''                      //头像路径
    },
    getters: {
        activeName: state => {
            let activeName = state.activeName
            if(!activeName){
                activeName = JSON.parse(window.sessionStorage.getItem('activeName')) 
            }
            return activeName
        },
        showAside: state => {
            let showAside = state.showAside
            if(!showAside){
                showAside = JSON.parse(window.sessionStorage.getItem('showAside')) 
            }
            return showAside
        },
        loginIn: state => {
            let loginIn = state.loginIn
            if(!loginIn){
                loginIn = JSON.parse(window.sessionStorage.getItem('loginIn')) 
            }
            return loginIn
        },
        isActive: state => {
            let isActive = state.isActive
            if(!isActive){
                isActive = JSON.parse(window.sessionStorage.getItem('isActive')) 
            }
            return isActive
        },
        activeOrder: state => {
            let activeOrder = state.activeOrder
            if(!activeOrder){
                activeOrder = JSON.parse(window.sessionStorage.getItem('activeOrder')) 
            }
            return activeOrder
        },      
        srcUrl: state => {
            let srcUrl = state.srcUrl
            if(!srcUrl){
                srcUrl = JSON.parse(window.sessionStorage.getItem('srcUrl')) 
            }
            return srcUrl
        },
    },
    mutations: {
        setActiveName: (state,activeName) => {
            state.activeName = activeName
            window.sessionStorage.setItem('activeName',JSON.stringify(activeName))
        },
        setShowAside: (state,showAside) => {
            state.showAside = showAside
            window.sessionStorage.setItem('showAside',JSON.stringify(showAside))
        },
        setLoginIn: (state,loginIn) => {
            state.loginIn = loginIn
            window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn))
        },
        setIsActive: (state,isActive) => {
            state.isActive = isActive
            window.sessionStorage.setItem('isActive',JSON.stringify(isActive))
        },
        setActiveOrder: (state,activeOrder) => {
            state.activeOrder = activeOrder
            window.sessionStorage.setItem('activeOrder',JSON.stringify(activeOrder))
        },
        setSrcUrl: (state,setSrcUrl) => {
            state.setSrcUrl = setSrcUrl
            window.sessionStorage.setItem('setSrcUrl',JSON.stringify(setSrcUrl))
        },
    }
}

export default configure