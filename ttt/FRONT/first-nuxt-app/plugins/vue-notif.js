import  Vue from 'vue'
import Notif from 'vue-notification'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
const VueInputMask = require('vue-inputmask').default

Vue.use(VueInputMask)
Vue.use(Notif)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
