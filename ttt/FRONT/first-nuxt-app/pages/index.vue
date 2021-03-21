<template>
  <main role="main">



    <main role="main">

      <section class="jumbotron text-center mm">
        <div class="container">
          <h1 class="jumbotron-heading text-light">Книжный магазин</h1>
          <p class="lead text-light">У нас вы можете найти все самые интересные книги!</p>

        </div>
      </section>

      <div class="album py-5 mmm">
        <div class="container">

          <input type="checkbox" id="checkbox" v-model="flag">
          <label for="checkbox">Поиск по автору</label>
          <div style="margin-bottom: 60px" ><input class="form-control mr-sm-2" type="text" placeholder="Поиск" v-model="search" aria-label="Search"></div>



          <div class="row">

              <div v-for="b in books" :key="b.id" class="col-md-4">
                  <div class="card mb-4 box-shadow">
                      <img class="card-img-top" v-bind:src="b.image" alt="Card image cap">
                      <div class="card-body">
                          <p class="card-text text-center">{{b.title}}</p>
                        <p class="card-text">{{b.author}}</p>
                          <div class="d-flex justify-content-between align-items-center">
                              <div class="btn-group">
                                <button type="button" @click="openEquipment(b)" class="btn btn-sm btn-outline-secondary">Подробнее</button>
                                <button v-if="$store.state.admin" type="button" @click="openEditor(b.id)" class="btn btn-sm btn-outline-secondary">Редактировать</button>
                              </div>
                              <small class="text-muted">{{b.count}} шт.</small>
                              <small>
                                  <span v-if="b.sale == 0">
                                      {{b.price}} руб.
                                  </span>
                                  <span v-else>
                                      <small><strike>{{b.price}} руб.</strike></small>
                                      <span style="color : red"> {{b.price / 100 * (100 - b.sale)}} руб. </span>
                                  </span>
                              </small>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
      </div>

    </main>

    <footer class="text-muted">
      <div class="container">
        <p class="float-right">
          <a style="color: #217162" href="#">Наверх</a>
        </p>

      </div>
    </footer>

  </main>
</template>

<script>
  const Cookie = process.client ? require('js-cookie') : undefined

  export default {
    data() {
      return {
        search : '',
        flag : ''
      }
    },
    async fetch({store}){
      await store.dispatch('product/loadProducts')
    },
    methods : {
      openEquipment(p){
        this.$router.push('/books/' + p.id)
      },
      openEditor(id){
        this.$router.replace('/admin/product-editor/' + id)
      },
    },
    computed : {
      books(){
        if(this.search === ''){
          if(this.$store.state.product.products.length === 0) this.$store.dispatch('product/loadProducts')
          return this.$store.state.product.products;
        }else{
          if(this.flag){
            return this.$store.state.product.products.filter(eq => eq.author.toLowerCase().includes(this.search.toLowerCase()))
          }else{
            return this.$store.state.product.products.filter(eq => eq.title.toLowerCase().includes(this.search.toLowerCase()))
          }
        }
      }
    },

  }
</script>


<style scoped>


  .mm{
    background-color: #29ac93;
    background-image: url("http://localhost:3001/img/back.png");
    filter: grayscale(80%);
  }



</style>
