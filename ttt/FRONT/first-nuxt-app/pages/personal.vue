<template>
    <div class="container">
      <h3>Вы вошли как {{this.$store.state.username}}</h3>
      <div class="" v-for="(order,index) in orders" v-bind:key="order.id">
        <div v-if="index % 2 == 0" class="gray row wrrrr" >
          <div class="col-3">
            <p><strong>Номер заказа</strong> : {{order.id}}</p>
            <p><strong>Адрес Доставки</strong> : {{order.address}}</p>
            <p v-if="order.accepted">
              <strong style="color: chartreuse">Заказ принят и отправлен</strong>
            </p>
            <p v-else>
              <strong style="color: red">Заказ еще не приняли</strong>
            </p>
          </div>
          <div class="col-5">
            <ul>
              <li v-for="card in order.sellingEntrySet">
                <strong>{{card.product.author}} - {{card.product.title}}</strong> : {{card.count}} x {{Math.ceil(card.price * (1 - card.sale / 100))}} = {{Math.ceil(card.price * (1 - card.sale / 100) * card.count)}} руб.
              </li>
              <hr color="black">
              <strong>Общая стоимость : {{countPrice(order)}} руб.</strong>
            </ul>
          </div>
          <div class="col-4">
            <button v-if="!order.accepted" style="margin-top: 20px !important;" @click="deleteOrder(order.id)" class=" btn btn-danger my-2 my-sm-0">
              Удалить заказ
            </button>
          </div>
        </div>
        <div v-else class="row wrrrr">
          <div class="col-3">
            <p><strong>Номер заказа</strong> : {{order.id}}</p>
            <p><strong>Офорормил заказ</strong> : {{order.user.username}}</p>
            <p v-if="order.accepted">
              <strong style="color: chartreuse">Заказ принят и отправлен</strong>
            </p>
            <p v-else>
              <strong style="color: red">Заказ еще не приняли</strong>
            </p>
          </div>
          <div class="col-5">
            <ul>
              <li v-for="card in order.sellingEntrySet">
                <strong>{{card.product.author}} - {{card.product.title}}</strong> : {{card.count}} x {{Math.ceil(card.price * (1 - card.sale / 100))}} = {{Math.ceil(card.price * (1 - card.sale / 100) * card.count)}} руб.
              </li>
              <hr color="black">
              <strong>Общая стоимость : {{countPrice(order)}} руб.</strong>
            </ul>
          </div>
          <div class="col-4">
            <button v-if="!order.accepted" style="margin-top: 20px !important;" @click="deleteOrder(order.id)" class=" btn btn-danger my-2 my-sm-0">
              Удалить заказ
            </button>
          </div>
        </div>

      </div>
    </div>
</template>

<script>
    import Index from "./admin/index";
    export default {
      components: {Index},
      middleware : 'authenticated',
        name: "personal",
        data() {
          return {
            accepted : ''
          }
        },
        computed : {
          color : function(index){
            console.log(index)
            return ''
          },
          orders(){
            if(this.accepted){
              return this.$store.state.orders.filter(i => i.user.username === this.$store.state.username && !i.accepted)
            }else{
              return this.$store.state.orders.filter(i => i.user.username === this.$store.state.username)
            }

          }
        },
        methods : {
          countPrice(ordere){
            let res = 0;
            ordere.sellingEntrySet.forEach(card => {
              res += card.count * Math.ceil(card.price * (1 - card.sale / 100))
            })
            return res;
          },
          deleteOrder(id){
            this.$store.dispatch('deleteOrder',id)
          }
        }
    }
</script>

<style scoped>
  .gray{
    background-color: rgba(128, 128, 128, 0.11);
  }
</style>
