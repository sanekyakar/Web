const cookieparser = process.server ? require('cookieparser') : undefined
import jwt_decode from "jwt-decode";

export const state = () => {
  return {
    username : null,
    auth: null,
    admin : null,
    basket : [],
    orders : []
  }
}
export const mutations = {
  setUsername(state,username){
    state.username = username
  },
  setAuth (state, auth) {
    state.auth = auth
  },
  setAdmin (state,isAdmin) {
    state.admin = isAdmin
  },
  setBasket(state,data){
    state.basket = data
  },
  setOrders(state,data){
    state.orders = data
  }
}
export const actions = {
  async nuxtServerInit ({ commit }, { req }) {
    this.$axios.setHeader('Content-Type', 'application/json;charset=UTF-8', [
      'post'
    ])
    this.$axios.setToken(false)
    let username = 'guest'
    let auth = null
    let isAdmin = null
    if (req.headers.cookie) {
      const parsed = cookieparser.parse(req.headers.cookie)
      try {
        auth = parsed.auth
        this.$axios.setToken(auth, 'Bearer')
        let roles = jwt_decode(auth).roles
        username = jwt_decode(auth).username
        if(roles.length === 2) isAdmin = true
      } catch (err) {
        console.log("куки потухли")
      }
    }
      await this.$axios.get('http://localhost:3001/basket/' + username).then( r =>{
         commit('setBasket',r.data)
      }).catch( () => {commit('setBasket',[])})
      await this.$axios.get('http://localhost:3001/order/all').then( r =>{
        commit('setOrders',r.data)
      }).catch( () => {commit('setOrders',[])})
    commit('product/loadALL')
    commit('setAuth', auth)
    commit('setAdmin',isAdmin)
    commit('setUsername',username)
  },

  addOrder({commit},data){
    this.$axios.post('http://localhost:3001/order',{
      username : data.username,
      code : data.code
    }).then( r =>{
      commit('setOrders',r.data)
      this.$axios.get('http://localhost:3001/basket/' + data.username).then( r =>{
        commit('setBasket',r.data)
      }).catch( () => {commit('setBasket',[])})
    })
  },

  deleteOrder({commit},id){
    console.log('deleting ' + id)
    this.$axios.post('http://localhost:3001/order/delete/' + id).then( r =>{
      commit('setOrders',r.data)
    })
  },

  acceptOrder({commit},id){
    this.$axios.post('http://localhost:3001/order/accept/' + id).then( r =>{
      commit('setOrders',r.data)
    })
  },

  addToBasket({commit},data) {
      this.$axios.post('http://localhost:3001/basket/add',{
        username : data.username,
        id : data.id
      }).then( r =>{
        commit('setBasket',r.data)
      })
  },

  deleteFromBasket({commit},data){
    this.$axios.post('http://localhost:3001/basket/delete',{
      username : data.username,
      id : data.id
    }).then( r =>{
      commit('setBasket',r.data)
    })
  },

  editBasket({commit},data){
    this.$axios.post('http://localhost:3001/basket/edit',{
      id : data.id,
      username : data.username,
      count : data.count,
    }).then( r =>{
      commit('setBasket',r.data)
    })
  }
}
