<template>
    <div class="container ars">
      <h1 class="text-center">Списки заказов</h1>
      <div style="margin-bottom: 30px"><input class="form-control mr-sm-2"  type="text" placeholder="Введите номер заказа или логин пользователя" v-model="search" aria-label="Search"></div>
      <div class="card text-center mb-5" v-for="order in orders" v-bind:key="order.id">
        <div class="card-header">
          <small>Номер заказа : {{order.id}}</small>
          <p><strong>Офорормил заказ</strong> : {{order.user.username}}</p>
          <p><strong>Адрес Доставки</strong> : {{order.address}}</p>
          <p><strong>Детали заказа</strong> : {{order.details}}</p>
          <p v-if="order.accepted">
            <strong style="color: chartreuse">Заказ принят и отправлен</strong>
          </p>
          <p v-else>
            <strong style="color: red">Заказ еще не приняли</strong>
          </p>
        </div>
        <div class="card-body">
          <ul>
            <p v-for="(card,index) in order.sellingEntrySet">
              <strong>{{index+1}}. {{card.product.author}} - {{card.product.title}}</strong> : {{card.count}} x {{Math.ceil(card.price * (1 - card.sale / 100))}} = {{Math.ceil(card.price * (1 - card.sale / 100) * card.count)}} руб.
            </p>
            <hr color="black">
            <strong>Общая стоимость : {{countPrice(order)}} руб.</strong>
          </ul>
        </div>
        <div class="card-footer text-muted">
          <button v-if="!order.accepted" style="margin-top: 20px !important;" @click="deleteOrder(order.id)" class=" btn btn-danger my-2 my-sm-0">
            Удалить заказ
          </button>
          <button v-if="$store.state.admin && !order.accepted" style="margin-top: 20px !important;" @click="acceptOrder(order.id)" class="btn btn-success my-2 my-sm-0">
            Принять заказ
          </button>
        </div>
      </div>
    </div>
</template>

<script>
    export default {
        middleware: 'admin',
        name: "index",
        data() {
          return {
            accepted : '',
            search : ''
          }
        },
        computed : {
          orders(){
            if(this.search == ''){
              return this.$store.state.orders
            }else{
              return this.$store.state.orders.filter(order =>
                order.id.toString().toLowerCase().includes(this.search.toLowerCase()) || order.user.username.toLowerCase().includes(this.search.toLowerCase())
              )
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
          },
          acceptOrder(id){
            this.$store.dispatch('acceptOrder',id)
          }

        }
    }
</script>

<style scoped>
  .ars{
    background-color: rgba(71, 73, 78, 0.16);
  }
</style>
