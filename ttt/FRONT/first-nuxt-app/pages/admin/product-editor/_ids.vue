<template>
  <div>
    <div class="container">
      <notifications group="f"/>
      Форма изменения товара
      <div class="form-group">
        <label for="name">
          Название :
        </label>
        <input type="text" name="name" v-model='title' class="form-control w-50" id="name"/>
      </div>
      <div class="form-group">
        <label for="author">
          Автор :
        </label>
        <input type="text" name="name" v-model='author' class="form-control w-50" id="author"/>
      </div>
      <div class="form-group">
        <label for="text">
          Начало книги (Пробная часть) :
        </label>
        <textarea v-model="text" name="name" class="form-control w-50" id="text"/>
      </div>

      <div class="form-group">
        <label for="price">
          Цена :
        </label>
        <input type="number" v-model="price" step="0.01" name="price" class="form-control w-50" id="price"/>
      </div>
      <div class="form-group">
        <label for="description">
          Описание :
        </label>
        <textarea v-model="description" name="name" class="form-control w-50" id="description"/>
      </div>
      <div class="form-group">
        <label for="sale">
          Скидка :
        </label>
        <input type="number" name="sale" v-model="sale" class="form-control w-50 " id="sale"/>
      </div>
      <div class="form-group">
        <label for="count">
          Кол-во :
        </label>
        <input type="number" name="count" v-model="count" class="form-control w-50 " id="count"/>
      </div>
      <div class="form-group">
        <label for="count-p">
          Страниц :
        </label>
        <input type="number" name="count" v-model="pages" class="form-control w-50 " id="count-p"/>
      </div>
      <button type="button" @click="postUpdate" class="btn btn-primary mt-3">Обновить</button><br>
    </div>
  </div>
</template>

<script>
    export default {
        middleware: 'admin',
        name: "_ids",
        async created(){
          await this.load()
          this.p =  this.$store.state.product.products.filter(p => p.id == this.$route.params.ids)[0]
          if(this.p !== undefined){
            this.id = this.p.id
            this.title = this.p.title
            this.count = this.p.count
            this.sale = this.p.sale
            this.description = this.p.description
            this.price = this.p.price
            this.author = this.p.author
            this.pages = this.p.pages
            this.text = this.p.text
          }
        },
        data() {
          return {
            p : '',
            id : '',
            title : '', //
            description : '',//
            author : '',
            text : '',
            pages : '',//
            price : '',//
            sale : '',//
            count : '',//
          }
        },
      methods : {
        load(){
          if(this.$store.state.product.products.length == 0){
            this.$store.dispatch('product/loadProducts')
            setTimeout(() => {
              this.p =  this.$store.state.product.products.filter(p => p.id == this.$route.params.ids)[0]
              if(this.p !== undefined){
                this.id = this.p.id
                this.title = this.p.title
                this.count = this.p.count
                this.sale = this.p.sale
                this.description = this.p.description
                this.price = this.p.price
                this.author = this.p.author
                this.pages = this.p.pages
                this.text = this.p.text
              }
            },1000)
          }
        },
        postUpdate(){
          const formData = new FormData()
          formData.append('id',this.id)
          formData.append('title',this.title)
          formData.append('count',this.count)
          formData.append('sale',this.sale)
          formData.append('author',this.author)
          formData.append('price',this.price)
          formData.append('pages',this.pages)
          formData.append('description',this.description)
          formData.append('text',this.text)
          this.$axios.put('http://localhost:3001/products',formData,{
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then( (response) => {
            this.$store.dispatch('product/loadProducts')
            this.$router.push('/books/' + response.data.id)
          }).catch(this.$notify({
            'group' : 'f',
            'title' : 'Ошибка',
            'type' : 'warn'
          }))
        }
      }
    }
</script>

<style scoped>

</style>
