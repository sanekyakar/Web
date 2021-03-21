<template>
  <div class="container center">
    <notifications group="f"/>
    <h3 class="text-center">Новая книга</h3>
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
      <label for="description">
        Описание :
      </label>
      <textarea v-model="description" name="name" class="form-control w-50" id="description"/>
    </div>
  <div class="form-group">
    <label for="price">
      Цена :
    </label>
    <input type="number" v-model="price" step="0.01" name="price" class="form-control w-50" id="price"/>
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
  <div>
    <p>Загрузите обложку книги</p>
    <p><input type="file" @change="onFileChange" accept="image/*,image/jpeg,image/webp"></p>
  </div>
  <button type="button" @click="postCreation" class="btn btn-primary mt-3">Добавить</button><br>
  </div>
</template>

<script>
    export default {
        middleware: 'admin',
        name: "product-creation",
        data() {
          return {
            title : '', //
            description : '',//
            author : '',
            text : '',
            pages : '',//
            price : '',//
            FILE : null,//
            sale : '',//
            count : '',//
          }
        },
        methods : {
          onFileChange(event){
            this.FILE = event.target.files[0]
            // console.log(this.$store.commit('product/addProduct','qqq'))
            // console.log(this.$store.state.product.products)
          },
          postCreation(){
            console.log(this.$data)
            const formData = new FormData()
            formData.append('title',this.title)
            formData.append('count',this.count)
            formData.append('sale',this.sale)
            formData.append('author',this.author)
            formData.append('price',this.price)
            formData.append('pages',this.pages)
            formData.append('description',this.description)
            formData.append('text',this.text)
            formData.append('file',this.FILE)
            this.$axios.post('http://localhost:3001/products',formData,{
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then( (response) => {
                this.$store.commit('product/addProducts',response.data)
                this.$router.push('/books/' + response.data.id)
              console.log(response.data)
            }).catch(err => this.$notify({
              'group' : 'f',
              'title' : 'Что то пошло не так',
              'type' : 'error'
            }))
          }
        }
    }
</script>

<style scoped>

</style>
