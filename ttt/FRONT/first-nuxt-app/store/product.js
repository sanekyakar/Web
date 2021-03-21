export const state = () => ({
  products : []
})


export const mutations = {
    setProducts(state,products){
      state.products = products
    },
    addProducts(state,products){
      state.products.push(products)
    },
    async loadALL(state){
      console.log('loading all')
      await this.$axios.get('http://localhost:3001/products').then( (response) => {
        state.products = response.data
      })
    }
}

export const actions = {
  deleteP({commit},id){
    this.$axios.delete('http://localhost:3001/products/' + id).then( () => {
      commit('loadALL')
    })
  },
  loadProducts({commit}){
    this.$axios.get('http://localhost:3001/products').then( (response) => {
      commit('setProducts',response.data)
    })
  },
}
