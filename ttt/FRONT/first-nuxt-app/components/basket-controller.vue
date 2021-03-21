<template>
    <div class="row wrr">
      <div class="col-2">
        <img v-bind:src="image" class="img-fluid">
      </div>
      <div class="col-4">
        <small>Код товара {{this.product}}</small><br>
        <strong style="font-size: +20px">{{this.author}} - {{this.title}}</strong>

      </div>
      <div class="col-2">
        <p><input type="number" size="3" v-model="count"  class="text-dark" min="1" @change="editBusket" :max="maxCount"></p>
      </div>
      <div class="col-2">
        <strong style="margin-top: 50px">{{Math.ceil(price / 100 * (100 - sale) * count)}} руб.</strong>
      </div>
      <div class="col-2">
        <p><button class="btn btn-secondary" @click="deleteItem">Удалить</button></p>
      </div>
    </div>
</template>

<script>
    export default {
        name: "basket-controller",
        props : {
          image : '',
          title : '',
          author : '',
          price : '',
          sale : '',
          count : '',
          maxCount : '',
          product : ''
        },
        methods : {
          editBusket(){
            let data = {
              id : this.product,
              username : this.$store.state.username,
              count : this.count
            }
            this.$store.dispatch('editBasket',data)
          },
          deleteItem(){
            let data = {
              id : this.product,
              username : this.$store.state.username
            }
            this.$store.dispatch('deleteFromBasket',data)
          }
        }
    }
</script>

<style scoped>

</style>
