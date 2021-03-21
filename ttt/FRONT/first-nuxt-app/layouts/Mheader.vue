<template>
  <div>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top back-color">
      <div class="navbar-brand">Бук-Шоп</div>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/">Главная</NuxtLink>
          </li>
          <li v-if="$store.state.admin" class="nav-item">
            <NuxtLink class="nav-link" to="/admin">Управление заказами</NuxtLink>
          </li>
          <li v-if="$store.state.admin" class="nav-item">
            <NuxtLink class="nav-link" to="/admin/product-creation">Новая Книга</NuxtLink>
          </li>
          <li v-if="$store.state.auth" class="nav-item">
            <NuxtLink class="nav-link" to="/personal">Личный кабинет</NuxtLink>
          </li>
          <li v-if="$store.state.auth" class="nav-item">
            <NuxtLink class="nav-link" to="/basket">Корзина</NuxtLink>
          </li>
        </ul>
        <div class="form-inline mt-2 mt-md-0">
          <div v-if="$store.state.auth" class="text-light">
            <button @click="logout" class="btn btn-outline-success my-2 my-sm-0">
              Выйти
            </button>
          </div>
          <div v-else>
            <NuxtLink to="/login">
              <button class="btn btn-outline-success my-2 my-sm-0" >Войти</button>
            </NuxtLink>
          </div>
        </div>
      </div>
    </nav>
    <div style="height: 100px"></div>
  </div>
</template>

<script>

    const Cookie = process.client ? require('js-cookie') : undefined


    export default {
        name: "header",
        methods: {
            logout () {
                Cookie.remove('auth')
                this.$axios.setToken(false)
                this.$store.commit('setAuth', null)
                this.$store.commit('setAdmin', null)
                this.$store.commit('setBasket',[])
                this.$store.commit('setUsername', 'Гость')
            }
        }
    }
</script>

<style scoped>
  .back-color{
    background-color: #217162;
  }
  .mtt{
    margin: 5px;
  }
  .mttt{
    margin-right: 20px;
  }
</style>
