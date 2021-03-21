<template>
  <div class="text-center mm">
  <div class="form-signin">
    <h1 class="h3 mb-3 font-weight-normal">Авторизация</h1>
    <label for="inputEmail" class="sr-only">Имя пользователя</label>
    <input type="email" id="inputEmail" v-model="username" class="form-control" placeholder="Имя пользователя" required autofocus>
    <label for="inputPassword" class="sr-only">Пароль</label>
    <input type="password" id="inputPassword" v-model="password" class="form-control" placeholder="Пароль" required>
    <div class="mb-3">
      Впервые у нас?
      <NuxtLink style="color: #217162" to="/registration">Зарегестирутесь.</NuxtLink>
    </div>
    <button class="btn btn-lg btn-primary btn-block" @click="postLogin">Войти</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
    </div>
    <notifications group="f"/>
  </div>
</template>

<script>

  import jwt_decode from "jwt-decode";
  const Cookie = process.client ? require('js-cookie') : undefined

  export default {
    middleware: 'notAuthenticated',
    data() {
      return {
        username : '',
        password : ''
      }
    },
    methods: {
      notificate(msg,t){
        this.$notify({
          'group' : 'f',
          'title' : msg,
          'type' : t
        })
      },
      async postLogin() {

        let data  = {
          username : this.username,
          password : this.password
        }

        if(this.username.length === 0 || this.password.length === 0){
          this.notificate('Введите данные коректно!','error')
          return
        }

        const auth = {
          accessToken: this.$axios.post('http://localhost:3001/auth/login',data).then( (response) => {
            let roles = jwt_decode(response.data.token).roles
            console.log(roles.length)
            if(roles.length === 2) this.$store.commit('setAdmin' ,true)
              this.$store.commit('setAuth', response.data.token)
              Cookie.set('auth', response.data.token)
              this.$axios.setToken(response.data.token, 'Bearer')
              this.$router.push('/')
              let username = jwt_decode(response.data.token).username
              this.$store.commit('setUsername',username)
              this.$axios.get('http://localhost:3001/basket/' + username).then( r =>{
                this.$store.commit('setBasket',r.data)
              }).catch( () => {this.$store.commit('setBasket',[])})
            }).catch(err => {
              this.notificate('Пользователя не существует','error')
          })
        }

      }
    }
  }
</script>

<style scoped>


  .form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: auto;
  }
  .form-signin .checkbox {
    font-weight: 400;
  }
  .form-signin .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="email"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }

</style>
