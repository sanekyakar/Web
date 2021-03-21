<template>
  <div class="text-center">
    <NuxtLink to="/login">назад</NuxtLink>
    <div class="form-signin">
      <h1 class="h3 mb-3 font-weight-normal">Введите данные для регистрации</h1>
      <label for="inputEmail" class="sr-only">Почта</label>
      <input type="email" id="inputEmail" v-model="username" class="form-control" placeholder="Почта" required autofocus>
      <label for="inputPassword" class="sr-only">Пароль</label>
      <input type="password" id="inputPassword" v-model="password" class="form-control" placeholder="Пароль" required>
      <button class="btn btn-lg btn-primary btn-block" @click="postRegistration">Зарегестрироваться</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
    </div>
    <notifications group="f"/>
  </div>
</template>

<script>
    export default {
      name: "registration",
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
        postRegistration() {
          let data  = {
            username : this.username,
            password : this.password
          }

          if(this.username.length === 0 || this.password.length === 0){
            this.notificate('Введите данные коректно!','error')
            return
          }

          this.$axios.post('http://localhost:3001/auth/register',data).then( (response) => {
            this.$router.push('/login')

          }).catch(err => {
            this.notificate('Пользователь уже существует','error')
          })

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
