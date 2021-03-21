<template>
  <div class="container">


    <notifications group="f"/>
    <h3 class="d-inline-flex text-right">Ваша корзина на сумму : {{basketPrice}} руб.</h3>
    <button  v-if="code.length != 0 &&  basketPrice != 0" @click="buy" style=" margin-left: 30px" class="d-inline-flex w-25 btn btn-danger my-2 my-sm-0" >
      <p class="text-light" style="margin-bottom: 0px">Оформить заказ</p>
    </button>
    <button v-else disabled style="margin-left: 30px" class=" w-25 btn btn-danger my-2 my-sm-0" >
      <p class="text-light" style="margin-bottom: 0px">Оформить заказ</p>
    </button>
    <div style="display: flex; margin-top: 50px">
      <input type="text" v-model="code" class="form-control w-75"  placeholder="Введите адрес доставки" required autofocus>
    </div>
    <hr style="color: black">

    <div>
      <div v-for="b in basket">
          <basket-controller :count="b.count"
                             :max-count="b.product.count"
                             :product="b.product.id"
                             :author="b.product.author"
                             :title="b.product.title"
                             :image="b.product.image"
                             :price="b.product.price"
                             :sale="b.product.sale"
                             :name="b.product.name"
          ></basket-controller>
      </div>
    </div>
  </div>
</template>

<script>
    import BasketController from "../components/basket-controller";
    export default {
      components: {BasketController},
      middleware: 'authenticated',
        name: "basket",
        data() {
          return {
            code : ''
          }
        },
        computed : {
          basket(){
            return this.$store.state.basket
          },
          basketPrice(){
            let res = 0;
            this.$store.state.basket.forEach(i => {
              res += (i.product.price * i.count) * (1 - i.product.sale / 100)
            })
            return Math.ceil(res);
          }
        },
        methods : {
          buy(){
            let data = {
              code : this.code,
              username : this.$store.state.username
            }
            this.$store.dispatch('addOrder',data)
            this.$notify({
              'group' : 'f',
              'title' : 'Заказ успешно оформлен',
              'text' : 'Вы можете ознакомиться с деталями в личном кабинете',
              'type' : 'success'
            })
          }
        }
    }
</script>

<style scoped>

</style>
