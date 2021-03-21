<template>
  <div>
    <div class="container">
      <notifications group="f"/>
      <div class="row">
        <div class="col-6">
          <h3 class="text-center">{{book.author}} : {{book.title}}</h3><hr color="black">
          <p>
            <strong>Страниц : </strong> {{book.pages}} шт.
          </p>
          <p>
            <strong>Цена : </strong> {{book.price}} руб.
          </p>
          <p>
            <strong>Скидка : </strong> {{book.sale}} %
          </p>
          <button @click="addToBusket" class="btn btn-outline-success mm my-2 my-sm-0 ">
            Добавить в корзину
          </button>
          <h4 class="text-center">Описание</h4>
          <p>
            {{book.description}}
          </p>
        </div>
        <div class="col-6">
          <img class="img-fluid" v-bind:src="book.image">
        </div>
      </div>
      <div>
        <h4 class="text-center" >Начало книги</h4>
        <h6>
          {{book.text}}
        </h6>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "_ids",
        async fetch(){
          if(this.$store.state.product.products.length === 0){
            await this.$store.dispatch('product/loadProducts')
            setTimeout(() => {this.book = this.$store.state.product.products.filter(p => p.id == this.$route.params.ids)[0];},100)
          }else{
            this.book = this.$store.state.product.products.filter(p => p.id == this.$route.params.ids)[0]
          }
        },
        data() {
          return {
            book : '',
          }
        },
      methods : {
        addToBusket(){
          if(!this.$store.state.auth){
            this.$notify({
              'group' : 'f',
              'title' : 'Зайдите в свой аккаунт',
              'type' : 'warn'
            })
            setTimeout(() => {
              this.$router.replace('/login')
            },500)
          }else{
            let data = {
              id : this.$route.params.ids,
              username : this.$store.state.username
            }
            console.log(this.$store.state.basket.filter(i => i.product.id == data.id).length)
            if(this.$store.state.basket.filter(i => i.product.id == data.id).length == 1){
              this.$notify({
                'group' : 'f',
                'title' : 'Товар уже в корзине',
              })
            }else {
              this.$store.dispatch('addToBasket', data)
            }

          }
        },
        notify(){
          this.$notify({
            'group' : 'f',
            'title' : 'Товар закончился',
            'type' : 'warn'
          })
        }
      }
    }
</script>

<style scoped>

</style>
